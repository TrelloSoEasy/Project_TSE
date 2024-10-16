package com.sparta.tse.config;

import com.sparta.tse.domain.user.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long userId;

    private final String nickname;

    private final String email;

    private final UserRole userRole;


    public AuthUser(Long userId, String nickname, String email, UserRole userRole){
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.userRole = userRole;
    }

}
