package com.sparta.tse.domain.workspace.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceUpdateResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.service.WorkspaceService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ApiResponse<WorkspacePostResponseDto> postWorkspace(@RequestBody WorkspacePostRequestDto requestDto) {
        WorkspacePostResponseDto responseDto = workspaceService.postWorkspace(requestDto);
        return ApiResponse.onSuccess(responseDto);
    }
    public ApiResponse<WorkspaceUpdateResponseDto> updateWorkspace(@RequestBody WorkspaceUpdateRequestDto requestDto) {
        WorkspaceUpdateResponseDto responseDto = workspaceService.updateWorkspace(requestDto);
        return ApiResponse.onSuccess(responseDto);
    }
}
