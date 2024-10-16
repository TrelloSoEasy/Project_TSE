package com.sparta.tse.domain.invitation.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.invitation.dto.request.InvitationPostRequestDto;
import com.sparta.tse.domain.invitation.dto.request.postInvitationRequestDto;
import com.sparta.tse.domain.invitation.entity.Invitation;
import com.sparta.tse.domain.invitation.entity.InvitationStatus;
import com.sparta.tse.domain.invitation.repository.InvitationRepository;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
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
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional
    public void postInvitation(Long workspaceId,InvitationPostRequestDto requestDto, AuthUser authUser) {
        //초대하고싶은 워크스페이스의 존재 확인
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        //워크스페이스 정원이 다찼는지 확인하는 로직 추가 필요

        //초대를 받는 사람이 존재하는지 확인하는 로직
        User receiveUser = userRepository.findByEmail(requestDto.getReceiverUserEmail()).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_RECEIVING_USER));
        //초대를 보낸 사람이 존재하는지도 확인
        User sendingUser = userRepository.findByEmail(authUser.getEmail()).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_SENDING_USER));
        //동일한 초대가 이미 있는지 확인하는 로직 필요 동일하다
        Invitation isItExists = invitationRepository.findByReceivingUserAndSendingUserAndInvitationStatusAndWorkspaceId(
                receiveUser,
                sendingUser,
                InvitationStatus.Pending,
                workspaceId
        );
        //초대가 있다고 뜬다면 중복 초대가 있는것이므로 예외 발생
        if(isItExists !=null) {
            throw new ApiException(ErrorStatus._INVITATION_ALREADY_EXISTS);
        }
        //초대 생성
        Invitation invitation = new Invitation(sendingUser,receiveUser,workspace);
        //초대 세이브
        invitationRepository.save(invitation);
    }

    @Transactional
    public void acceptInvitation(Long workspaceId,Long sendingUserId,AuthUser authUser) {
        User receiveUser = userRepository.findByEmail(authUser.getEmail()).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_RECEIVING_USER));
        User sendingUser = userRepository.findById(sendingUserId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_SENDING_USER));
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        //초대를 찾음
        Invitation isItExists = invitationRepository.findByReceivingUserAndSendingUserAndInvitationStatusAndWorkspaceId(
                receiveUser,
                sendingUser,
                InvitationStatus.Pending,
                workspaceId
        );
        //초대가 없으면 잘못된것
        if(isItExists==null) {
            throw new ApiException(ErrorStatus._NOT_FOUND_INVITATION);
        }
        //초대 승낙
        isItExists.acceptInvitation();
        //같은 워크스페이스id 이면서 초대를 보낸사람은 다른 초대를 전부 accepted로 변경
        List<Invitation> invitations = invitationRepository.findByReceivingUserAndInvitationStatusAndWorkspaceId(
                receiveUser,
                InvitationStatus.Pending,
                workspaceId
        );
        //이 초대들이 존재하면 모두 accepted로 변경하기
        if(!invitations.isEmpty()) {
            for(Invitation invitation : invitations) {
                invitation.acceptInvitation();
            }
        }

        WorkspaceMember workspaceMember = new WorkspaceMember(workspace,receiveUser, MemberRole.USER);
        workspace.addMember(workspaceMember);
        workspaceMemberRepository.save(workspaceMember);
    }
}
