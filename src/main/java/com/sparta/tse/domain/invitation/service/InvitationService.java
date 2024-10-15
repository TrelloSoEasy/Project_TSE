package com.sparta.tse.domain.invitation.service;

import com.sparta.tse.domain.invitation.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;


    public void postInvitation(Long workspaceId, String receiveUserEmail) {
        //초대하고싶은 워크스페이스와
    }
}
