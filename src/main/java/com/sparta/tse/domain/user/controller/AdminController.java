package com.sparta.tse.domain.user.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.dto.request.updateUserAuthorityRequestDto;
import com.sparta.tse.domain.user.service.UserService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/admin")
public class AdminController {
    private final UserService userService;

    // Admin 만 사용가능한 메소드 = @Secured("ROLE_ADMIN") 어노테이션 사용
    @PutMapping("/users/{userId}")
    public ApiResponse<String> updateUserAuthority(@PathVariable Long userId,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        userService.updateUserAuthority(userId,authUser);

        return ApiResponse.onSuccess("유저 권한 변경완료");
    }
}
