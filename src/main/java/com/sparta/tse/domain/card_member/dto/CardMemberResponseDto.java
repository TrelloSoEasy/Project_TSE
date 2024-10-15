package com.sparta.tse.domain.card_member.dto;

import com.sparta.tse.domain.card.dto.CardResponseDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import lombok.Getter;

@Getter
public class CardMemberResponseDto {

    private Long cardMemberId;
    private Long userId;
    private String userEmail;

    public static CardMemberResponseDto of(CardMember cardMember) {
        return new CardMemberResponseDto(cardMember.getCardMemberId(),
                cardMember.getUser().getUserId(),
                cardMember.getUser().getEmail());
    }

    private CardMemberResponseDto(Long cardMemberId, Long userId, String userEmail) {
        this.cardMemberId = cardMemberId;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
