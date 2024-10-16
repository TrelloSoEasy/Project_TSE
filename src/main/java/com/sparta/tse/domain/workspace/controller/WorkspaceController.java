package com.sparta.tse.domain.workspace.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.invitation.dto.request.postInvitationRequestDto;
import com.sparta.tse.domain.invitation.service.InvitationService;
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
    @PutMapping("/{workspaceId}")
    public ApiResponse<WorkspaceUpdateResponseDto> updateWorkspace(@RequestBody WorkspaceUpdateRequestDto requestDto,
                                                                   @PathVariable Long workspaceId) {
        WorkspaceUpdateResponseDto responseDto = workspaceService.updateWorkspace(workspaceId,requestDto);
        return ApiResponse.onSuccess(responseDto);
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<WorkspaceGetResponseDto>> getUserWorkspaces (@PathVariable Long userId) {

        List<WorkspaceGetResponseDto> responseDtoList = workspaceService.getWorkspaces(userId);
        return ApiResponse.onSuccess(responseDtoList);
    }
    //워크스페이스 초대 요청보내기
    @PostMapping("/{workspaceId}/invitation/{receiveUserEmail}")
    public void postInvitation(@PathVariable Long workspaceId,
                               @PathVariable String receiveUserEmail,
                               @AuthenticationPrincipal AuthUser authUser) {
        invitationService.postInvitation(workspaceId,receiveUserEmail,authUser);
    }

    //워크스페이스 초대 요청 받기
    @PostMapping("/{workspaceId}/invitation/{receiveUserEmail}/accept")
    public void acceptInvitation(@PathVariable Long workspaceId,
                                 @PathVariable String receiveUserEmail,
                                 @AuthenticationPrincipal AuthUser authUser) {
          invitationService.acceptInvitation(workspaceId,receiveUserEmail,authUser);
//        NotificationRequestDto requestDto = new NotificationRequestDto("MEMBER_ADDED", nickname);
//        notificationService.notifiyMemberAdded(requestDto);
    }
}
