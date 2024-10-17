package com.sparta.tse.domain.invitation.dto.response;

import com.sparta.tse.domain.invitation.dto.InvitationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InvitationGetResponseDto {
    private final List<InvitationDto> invitationDtoList=new ArrayList<>();

    public void add(InvitationDto Dto) {
        this.invitationDtoList.add(Dto);
    }
}
