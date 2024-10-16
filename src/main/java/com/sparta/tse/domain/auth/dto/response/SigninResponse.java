package com.sparta.tse.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SigninResponse {
    // 로그인 반환 값
    private final String bearerToken;

    public SigninResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
