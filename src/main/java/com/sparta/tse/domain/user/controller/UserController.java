package com.sparta.tse.domain.user.controller;

import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/user")
public class UserController {

    UserService userService;
    UserRepository userRepository;

    // 회원 탈퇴
    @DeleteMapping
    public void deletedUser (String email, String password){
       userService.deletedUser(email,password);
    }
}
