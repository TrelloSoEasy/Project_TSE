package com.sparta.tse.domain.user.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.invitation.dto.response.InvitationGetResponseDto;
import com.sparta.tse.domain.invitation.service.InvitationService;
import com.sparta.tse.domain.user.dto.DeleteUserRequestDto;
import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/users")
public class UserController {

    private final InvitationService invitationService;
    UserService userService;
    UserRepository userRepository;

    // 회원 탈퇴
    @DeleteMapping
    public void deletedUser (AuthUser authUser, @RequestBody DeleteUserRequestDto deleteUserRequestDto){
       userService.deletedUser(authUser, deleteUserRequestDto);
    }

    @GetMapping("/invitations")
    public ApiResponse<InvitationGetResponseDto> GetInvitations(@AuthenticationPrincipal AuthUser authUser) {
        InvitationGetResponseDto responseDto =invitationService.getInvitations(authUser);
        return ApiResponse.onSuccess(responseDto);
    }


}
