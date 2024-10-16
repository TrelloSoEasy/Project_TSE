package com.sparta.tse.domain.invitation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvitationPostRequestDto {
    private String receiverUserEmail;

    public InvitationPostRequestDto(String receiverUserEmail) {
        this.receiverUserEmail = receiverUserEmail;
    }
}
