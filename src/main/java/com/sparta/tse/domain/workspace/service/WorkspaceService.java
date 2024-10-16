package com.sparta.tse.domain.workspace.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.enums.UserRole;
import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceGetResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceUpdateResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import com.sparta.tse.domain.workspaceMember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspacePostResponseDto postWorkspace(WorkspacePostRequestDto requestDto,AuthUser authUser) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(()->new ApiException(ErrorStatus._NOT_FOUND_USER));

        UserRole userRole = user.getUserRole();


        Workspace workspace = new Workspace(requestDto.getWorkspaceName(), requestDto.getWorkspaceDescription());
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        return new WorkspacePostResponseDto(savedWorkspace.getWorkspaceId(),savedWorkspace.getName(),savedWorkspace.getDescription());
    }

    public WorkspaceUpdateResponseDto updateWorkspace(Long workspaceId, WorkspaceUpdateRequestDto requestDto) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        return null;
    }

    public List<WorkspaceGetResponseDto> getWorkspaces(Long userId) {
        List<Workspace> workspaceList = workspaceMemberRepository.findWorkspaceByUserId(userId);
        List<WorkspaceDto> workspaceDtoList = workspaceList.stream().map(WorkspaceDto::new).toList();

        return workspaceDtoList.stream().map(WorkspaceGetResponseDto::new).toList();
    }


}
