package com.sparta.tse.domain.workspace.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.invitation.dto.request.InvitationPostRequestDto;
import com.sparta.tse.domain.invitation.dto.request.postInvitationRequestDto;
import com.sparta.tse.domain.invitation.service.InvitationService;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceDeleteRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceGetResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceUpdateResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.service.WorkspaceService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE")
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final InvitationService invitationService;

    @PostMapping
    public ApiResponse<WorkspacePostResponseDto> postWorkspace(@RequestBody WorkspacePostRequestDto requestDto,
                                                               @AuthenticationPrincipal AuthUser authUser) {
        WorkspacePostResponseDto responseDto = workspaceService.postWorkspace(requestDto,authUser);
        return ApiResponse.onSuccess(responseDto);
    }
    //워크스페이스 이름,설명 변경
    @PutMapping("/{workspaceId}")
    public ApiResponse<Null> updateWorkspace(@RequestBody WorkspaceUpdateRequestDto requestDto,
                                             @PathVariable Long workspaceId,
                                             @AuthenticationPrincipal AuthUser authUser) {
        workspaceService.updateWorkspace(workspaceId,requestDto,authUser);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<WorkspaceGetResponseDto>> getUserWorkspaces (@PathVariable Long userId,
                                                                         @AuthenticationPrincipal AuthUser authUser) {

        List<WorkspaceGetResponseDto> responseDtoList = workspaceService.getWorkspaces(userId,authUser);
        return ApiResponse.onSuccess(responseDtoList);
    }
    //워크스페이스 초대 요청보내기
    @PostMapping("/{workspaceId}/invitation")
    public void postInvitation(@PathVariable Long workspaceId,
                               @AuthenticationPrincipal AuthUser authUser,
                               @RequestBody InvitationPostRequestDto requestDto) {
        invitationService.postInvitation(workspaceId,requestDto,authUser);
    }

    //워크스페이스 초대 요청 받기
    @PostMapping("/{workspaceId}/invitation/{sendingUserId}/accept")
    public void acceptInvitation(@PathVariable Long workspaceId,
                                 @PathVariable Long sendingUserId,
                                 @AuthenticationPrincipal AuthUser authUser) {
          invitationService.acceptInvitation(workspaceId,sendingUserId,authUser);
//        NotificationRequestDto requestDto = new NotificationRequestDto("MEMBER_ADDED", nickname);
//        notificationService.notifiyMemberAdded(requestDto);
    }

    @DeleteMapping("/{workspaceId}")
    public void deleteWorkspace(@AuthenticationPrincipal AuthUser authUser,
                                @PathVariable Long workspaceId,
                                @RequestBody WorkspaceDeleteRequestDto requestDto) {
        workspaceService.deleteWorkspace(authUser,workspaceId,requestDto);
    }
}
