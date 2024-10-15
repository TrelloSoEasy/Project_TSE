package com.sparta.tse.config;

import com.sparta.tse.domain.user.entity.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {

    private final Long authUserId;

    private final String nickname;

    private final String email;

    private final String password;

    private final UserRole userRole;


    public AuthUser(Long authUserId, String nickname, String email,String password, UserRole userRole){
        this.authUserId = authUserId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

}
