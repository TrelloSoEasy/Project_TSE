package com.sparta.tse.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/admin")
public class AdminController {

    // Admin 만 사용가능한 메소드 = @Secured("ROLE_ADMIN") 어노테이션 사용
}
