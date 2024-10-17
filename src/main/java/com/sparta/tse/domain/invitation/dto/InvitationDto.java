package com.sparta.tse.domain.invitation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvitationDto {
    private Long workspaceId;
    private String sendingUserEmail;

    public InvitationDto(Long workspaceId, String sendingUserEmail) {
        this.workspaceId = workspaceId;
        this.sendingUserEmail = sendingUserEmail;
    }
}
