package com.sparta.tse.domain.card_member.dto;

import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.entity.CardMemberRole;
import lombok.Getter;

@Getter
public class CardMemberResponseDto {

    private final Long cardMemberId;
    private final Long userId;
    private final String userEmail;
    private final CardMemberRole cardMemberRole;

    public static CardMemberResponseDto of(CardMember cardMember) {
        return new CardMemberResponseDto(cardMember.getCardMemberId(),
                cardMember.getUser().getUserId(),
                cardMember.getUser().getEmail(),
                cardMember.getRole());
    }

    private CardMemberResponseDto(Long cardMemberId,
                                  Long userId,
                                  String userEmail,
                                  CardMemberRole role) {
        this.cardMemberId = cardMemberId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.cardMemberRole = role;
    }
}
