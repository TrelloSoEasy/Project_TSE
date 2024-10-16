package com.sparta.tse.domain.workspace.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceDeleteRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspaceGetResponseDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import com.sparta.tse.domain.workspaceMember.entity.MemberRole;
import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
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
        //유저를찾는다
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(()->new ApiException(ErrorStatus._NOT_FOUND_USER));
        //실제로 있는 유저라면 워크스페이스 생성
        Workspace workspace = new Workspace(requestDto.getWorkspaceName(), requestDto.getWorkspaceDescription());
        //세이브
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        //그리고 멤버 테이블도 만들어 자동으로 ADMIN인 상태로 멤버테이블에도 저장
        WorkspaceMember workspaceMember = new WorkspaceMember(savedWorkspace,user, MemberRole.ADMIN);
        savedWorkspace.addMember(workspaceMember);
        //멤버테이블에도 저장
        workspaceMemberRepository.save(workspaceMember);

        return new WorkspacePostResponseDto(savedWorkspace.getWorkspaceId(),savedWorkspace.getName(),savedWorkspace.getDescription());
    }

    public void updateWorkspace(Long workspaceId, WorkspaceUpdateRequestDto requestDto, AuthUser authUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        //업데이트하려는 사람이 워크스페이스 멤버테이블에 있는 정보중 본인의 역할이 ADMIN인지 확인
        String memberRole = workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE));
        if(memberRole.equals(MemberRole.ADMIN.toString())){
            workspace.update(requestDto);
        }
        else {
            throw new ApiException(ErrorStatus._INVALID_USER_ROLE);
        }
    }

    public List<WorkspaceGetResponseDto> getWorkspaces(Long userId, AuthUser authUser) {
        List<Workspace> workspaceList = workspaceMemberRepository.findWorkspaceByUserId(userId);
        List<WorkspaceDto> workspaceDtoList = workspaceList.stream().map(WorkspaceDto::new).toList();

        return workspaceDtoList.stream().map(WorkspaceGetResponseDto::new).toList();
    }


    public void deleteWorkspace(AuthUser authUser, Long workspaceId, WorkspaceDeleteRequestDto requestDto) {
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_USER));
        if(user.getIsdeleted()) {
            throw new ApiException(ErrorStatus._DELETED_USER);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE)
        );
        String Role = workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE)
        );

        if(!(Role.equals(MemberRole.OWNER.toString()))) {
            throw new ApiException(ErrorStatus._NOT_PERMITTED_USER);
            //"해당 작업은 OWNER 권한을 가진 유저만 가능합니다"
        }
        workspaceRepository.delete(workspace);
    }
}
