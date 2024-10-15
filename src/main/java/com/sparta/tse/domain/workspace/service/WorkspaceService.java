package com.sparta.tse.domain.workspace.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceUpdateResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspacePostResponseDto postWorkspace(WorkspacePostRequestDto requestDto) {
        Workspace workspace = new Workspace(requestDto.getWorkspaceName(), requestDto.getWorkspaceDescription());
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        return new WorkspacePostResponseDto(savedWorkspace.getWorkspaceId(),savedWorkspace.getName(),savedWorkspace.getDescription());
    }

    public WorkspaceUpdateResponseDto updateWorkspace(WorkspaceUpdateRequestDto requestDto) {
        Workspace workspace = workspaceRepository.findById(requestDto.getId()).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        return null;
    }
}
