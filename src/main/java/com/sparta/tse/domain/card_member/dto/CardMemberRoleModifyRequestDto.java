package com.sparta.tse.domain.card_member.dto;

import com.sparta.tse.domain.card_member.entity.CardMemberRole;
import lombok.Getter;

@Getter
public class CardMemberRoleModifyRequestDto {
    
    private Long cardId;
    private Long memberId;
    private CardMemberRole cardMemberRole;
    
}
