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

    @PostMapping("/workspace")
    public ApiResponse<WorkspacePostResponseDto> postWorkspace(@RequestBody WorkspacePostRequestDto requestDto,
                                                               @AuthenticationPrincipal AuthUser authUser) {
        WorkspacePostResponseDto responseDto = workspaceService.postWorkspace(requestDto,authUser);
        return ApiResponse.onSuccess(responseDto);
    }
    //워크스페이스 이름,설명 변경
    @PutMapping("/workspace/{workspaceId}")
    public ApiResponse<Null> updateWorkspace(@PathVariable Long workspaceId,
                                             @RequestBody WorkspaceUpdateRequestDto requestDto,
                                             @AuthenticationPrincipal AuthUser authUser) {
        workspaceService.updateWorkspace(workspaceId,requestDto,authUser);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/users/workspaces")
    public ApiResponse<List<WorkspaceGetResponseDto>> getUserWorkspaces (@AuthenticationPrincipal AuthUser authUser) {

        List<WorkspaceGetResponseDto> responseDtoList = workspaceService.getWorkspaces(authUser);
        return ApiResponse.onSuccess(responseDtoList);
    }
    //워크스페이스 초대 요청보내기
    @PostMapping("/workspace/{workspaceId}/invitation")
    public void postInvitation(@PathVariable Long workspaceId,
                               @RequestBody InvitationPostRequestDto requestDto,
                               @AuthenticationPrincipal AuthUser authUser) {
        invitationService.postInvitation(workspaceId,requestDto,authUser);
    }

    //워크스페이스 초대 요청 받기
    @PostMapping("/workspace/{workspaceId}/invitation/{sendingUserId}")
    public void acceptInvitation(@PathVariable Long workspaceId,
                                 @PathVariable Long sendingUserId,
                                 @AuthenticationPrincipal AuthUser authUser) {
          invitationService.acceptInvitation(workspaceId,sendingUserId,authUser);
//        NotificationRequestDto requestDto = new NotificationRequestDto("MEMBER_ADDED", nickname);
//        notificationService.notifiyMemberAdded(requestDto);
    }

    @DeleteMapping("/workspace/{workspaceId}")
    public void deleteWorkspace(@PathVariable Long workspaceId,
                                @RequestBody WorkspaceDeleteRequestDto requestDto,
                                @AuthenticationPrincipal AuthUser authUser) {
        workspaceService.deleteWorkspace(authUser,workspaceId,requestDto);
    }
}
