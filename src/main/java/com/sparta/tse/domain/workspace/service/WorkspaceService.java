package com.sparta.tse.domain.workspace.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.enums.UserRole;
import com.sparta.tse.domain.user.repository.UserRepository;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceDeleteRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspace.dto.request.updateUserRoleRequestDto;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public WorkspacePostResponseDto postWorkspace(WorkspacePostRequestDto requestDto,AuthUser authUser) {
        //유저는 워크스페이스를 만들 수 없음.
        if(authUser.getUserRole().equals(UserRole.USER_ROLE)) {
            throw new ApiException(ErrorStatus._NOT_PERMITTED_USER);
        }
        if(requestDto.getWorkspaceName() == null || requestDto.getWorkspaceName().trim().isEmpty()) {
            throw new ApiException(ErrorStatus._BAD_REQUEST_INVALID_DATA);
        }
        //유저를찾는다
        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(()->new ApiException(ErrorStatus._NOT_FOUND_USER));

        Workspace workspace = new Workspace(requestDto.getWorkspaceName(), requestDto.getWorkspaceDescription());
        //세이브
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        //그리고 멤버 테이블도 만들어 자동으로 ADMIN인 상태로 멤버테이블에도 저장
        WorkspaceMember workspaceMember = new WorkspaceMember(savedWorkspace,user, MemberRole.OWNER);
        savedWorkspace.addMember(workspaceMember);
        //멤버테이블에도 저장
        workspaceMemberRepository.save(workspaceMember);

        return new WorkspacePostResponseDto(savedWorkspace.getWorkspaceId(),savedWorkspace.getName(),savedWorkspace.getDescription());
    }
    @Transactional
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

    public List<WorkspaceGetResponseDto> getWorkspaces(AuthUser authUser) {
        List<Workspace> workspaceList = workspaceMemberRepository.findWorkspaceByUserId(authUser.getUserId());
        List<WorkspaceDto> workspaceDtoList = workspaceList.stream().map(WorkspaceDto::new).toList();
        return workspaceDtoList.stream().map(WorkspaceGetResponseDto::new).toList();
    }

    @Transactional
    public void deleteWorkspace(AuthUser authUser, Long workspaceId,WorkspaceDeleteRequestDto requestDto) {
        if(requestDto.getDeleteCode()==null||requestDto.getDeleteCode().trim().isEmpty()){
            throw new ApiException(ErrorStatus._BAD_REQUEST);
        }
        if(!requestDto.getDeleteCode().equals("삭제하겠습니다")) {
            throw new ApiException(ErrorStatus._BAD_REQUEST);
        }
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
    @Transactional
    public void updateWorkspaceMemeberRole(Long workspaceId, updateUserRoleRequestDto requestDto, Long userId, AuthUser authUser) {
        if(!(Objects.equals(requestDto.getRole(), MemberRole.ADMIN.name()) || Objects.equals(requestDto.getRole(), MemberRole.USER.name()))){
            throw new ApiException(ErrorStatus._BAD_REQUEST);
        }

        User user = userRepository.findByEmail(authUser.getEmail()).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_USER)
        );
        if(user.getIsdeleted()) {
            throw new ApiException(ErrorStatus._DELETED_USER);
        }
        String role = workspaceMemberRepository.findRoleByEmail(user.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_USER)
        );

        if(role.equals(MemberRole.USER.name())) {
            throw new ApiException(ErrorStatus._NOT_PERMITTED_USER);
        }

        String isExitsUserInWorkspace= workspaceMemberRepository.findRoleByEmail(userId,workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_USER)
        );

        if(isExitsUserInWorkspace==null) {
           throw new ApiException(ErrorStatus._USER_NOT_IN_WORKSPACE);
        }

        WorkspaceMember workspaceMember = workspaceMemberRepository.findWorkspaceMemberByUserIdAndWorkspaceId(userId,workspaceId);

        if(Objects.equals(requestDto.getRole(), MemberRole.ADMIN.toString())) {
            workspaceMember.updateMemberRole(MemberRole.ADMIN);
        }
        if(Objects.equals(requestDto.getRole(), MemberRole.USER.toString())) {
            workspaceMember.updateMemberRole(MemberRole.USER);
        }
    }
}
