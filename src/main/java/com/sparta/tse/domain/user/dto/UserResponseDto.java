package com.sparta.tse.domain.user.dto;

import com.sparta.tse.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String email;

    public UserResponseDto(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUserId(), user.getEmail());
    }

}
