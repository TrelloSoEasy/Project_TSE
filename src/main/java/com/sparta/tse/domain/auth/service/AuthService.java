package com.sparta.tse.domain.auth.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.JwtUtil;
import com.sparta.tse.domain.auth.dto.request.SigninRequest;
import com.sparta.tse.domain.auth.dto.request.SignupRequest;
import com.sparta.tse.domain.auth.dto.response.SigninResponse;
import com.sparta.tse.domain.auth.dto.response.SignupResponse;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.enums.UserRole;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    // 이메일 유효성 검사 정규 표현식
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // 비밀번호 유효성 검사 정규 표현식
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // 회원가입
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        // 이메일 형식 유효성 검사
        if (!isValidEmail(signupRequest.getEmail())) {
            throw new ApiException(ErrorStatus._BAD_REQUEST_INVALID_EMAIL);
        }
        // 이메일 중복 확인
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ApiException(ErrorStatus._DUPLICATED_EMAIL);
        }
        // 비밀번호 형식 유효성 검사
        if (!isValidPassword(signupRequest.getPassword())) {
            throw new ApiException(ErrorStatus._INVALID_PASSWORD_FORM);
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        UserRole userRole = UserRole.of(signupRequest.getUserRole());

        User newUser = new User(
                signupRequest.getNickname(),
                signupRequest.getEmail(),
                encodedPassword,
                userRole
        );
        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(savedUser.getUserId(),savedUser.getNickname(), savedUser.getEmail(), userRole);

        return new SignupResponse(bearerToken);
    }

    // 이메일 유효성 검사 메서드
    private boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    // 비밀번호 유효성 검사 메서드
    private boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    // 로그인
    @Transactional
    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorStatus._PASSWORD_NOT_MATCHES);
        }

        String bearerToken = jwtUtil.createToken(user.getUserId(), user.getNickname(), user.getEmail(), user.getUserRole());

        return new SigninResponse(bearerToken);
    }

}
