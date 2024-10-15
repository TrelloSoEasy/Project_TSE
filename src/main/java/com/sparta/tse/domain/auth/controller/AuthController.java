package com.sparta.tse.domain.auth.controller;

import com.sparta.tse.domain.auth.dto.request.SigninRequest;
import com.sparta.tse.domain.auth.dto.request.SignupRequest;
import com.sparta.tse.domain.auth.dto.response.SigninResponse;
import com.sparta.tse.domain.auth.dto.response.SignupResponse;
import com.sparta.tse.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    // 로그인
    @PostMapping("/auth/signin")
    public SigninResponse signin(@Valid @RequestBody SigninRequest signinRequest) {
        return authService.signin(signinRequest);
    }
}
