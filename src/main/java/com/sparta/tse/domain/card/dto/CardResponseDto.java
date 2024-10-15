package com.sparta.tse.domain.card.dto;

import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.image.entity.Images;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardResponseDto {

    private Long cardList;
    private Long cardId;
    private String cardTitle;
    private String cardContent;
    private int cardSequence;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<CardComment> cardComments;
    private List<CardMember> cardMemberList;
    private List<Images> imagesList;

    public static CardResponseDto of(Long cardListId,
                                     Long cardId,
                                     String cardTitle,
                                     String cardContent,
                                     int cardSequence,
                                     LocalDateTime startAt,
                                     LocalDateTime endAt,
                                     List<CardComment> cardCommentsList,
                                     List<CardMember> cardMemberList,
                                     List<Images> imagesList) {
        return new CardResponseDto(cardListId,
                cardId,
                cardTitle,
                cardContent,
                cardSequence,
                startAt,
                endAt,
                cardCommentsList,
                cardMemberList,
                imagesList);
    }

}
