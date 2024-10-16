package com.sparta.tse.config;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final String BEARER_PREFIX = "Bearer";
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 토큰 유효시간 60분

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 토큰을 만들때 닉네임 추가.
    public String createToken(Long userId, String nickname, String email, UserRole userRole) {
        Date date = new Date();

        // 토큰에 BEARER 추가해서 반환. 총 7자
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(userId))
                        .claim("nickname", nickname)
                        .claim("email", email)
                        .claim("userRole", userRole)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 토큰 만료기간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ApiException(ErrorStatus._NOT_FOUND_TOKEN);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
