package com.sparta.tse.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignupResponse {
    // 회원가입 반환 값
    private final String bearerToken;

    public SignupResponse(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}
