package com.sparta.tse.domain.user.controller;

import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.dto.DeleteUserRequestDto;
import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void deletedUser (AuthUser authUser, @RequestBody DeleteUserRequestDto deleteUserRequestDto){
       userService.deletedUser(authUser, deleteUserRequestDto);
    }
}
