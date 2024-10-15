package com.sparta.tse.domain.card.dto;

import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card_member.dto.CardMemberResponseDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.image.dto.ImagesResponseDto;
import com.sparta.tse.domain.image.entity.Images;
import com.sparta.tse.domain.user.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardResponseDto {

    private Long cardListId;
    private Long cardId;
    private String cardTitle;
    private String cardContent;
    private String cardStatus;
    private int cardSequence;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<CommentResponseDto> cardComments;
    private List<CardMemberResponseDto> cardMemberList;
    private List<ImagesResponseDto> imagesList;

    private CardResponseDto(Long cardListId,
                            Long cardId,
                            String cardTitle,
                            String cardContent,
                            String cardStatus,
                            int cardSequence,
                            LocalDateTime startAt,
                            LocalDateTime endAt,
                            List<CardComment> commentList,
                            List<CardMember> cardMemberList,
                            List<Images> imageList) {

        this.cardListId = cardListId;
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.cardStatus = cardStatus;
        this.cardSequence = cardSequence;
        this.startAt = startAt;
        this.endAt = endAt;
        // 댓글 목록 가져오기
        this.cardComments = commentList.stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList());
        // 카드에 등록된 유저 가져오기
        this.cardMemberList = cardMemberList.stream()
                .map(CardMemberResponseDto::of)
                .collect(Collectors.toList());
        // 카드에 저장된 이미지 가져오기
        this.imagesList = imageList.stream()
                .map(ImagesResponseDto::of)
                .collect(Collectors.toList());
    }

    public static CardResponseDto of(Card card) {
        return new CardResponseDto(card.getCardList().getCardListId(),
                card.getCardId(),
                card.getCardTitle(),
                card.getCardContent(),
                card.getCardStatus(),
                card.getCardSequence(),
                card.getStartAt(),
                card.getEndAt(),
                card.getCommentList(),
                card.getCardMemberList(),
                card.getImageList()
        );
    }

}
