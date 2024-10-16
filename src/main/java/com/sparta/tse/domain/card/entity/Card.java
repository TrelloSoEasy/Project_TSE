package com.sparta.tse.domain.card.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.card.dto.request.CardModifyRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.comment.entity.CardComment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@Getter
public class Card extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "card_title", nullable = false, length = 100)
    private String cardTitle;

    @Column(name = "card_content", nullable = false, length = 2000)
    private String cardContent;

    @Column(name = "card_startAt", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "card_endAt", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "card_sequence")
    private Long cardSequence;

    @Column(name = "userId")
    private Long cardUserId;
    @ManyToOne
    @JoinColumn(name = "card_list_id")
    private CardList cardList;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<CardComment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<CardMember> cardMemberList = new ArrayList<>();


    private Card(String cardTitle, String cardContent, LocalDateTime startAt, LocalDateTime endAt, Long cardSequence, Long cardUserId, CardList cardList) {
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cardSequence = cardSequence;
        this.cardList = cardList;
        this.cardUserId = cardUserId;
    }


    // 정적 팩토리 메서드
    public static Card createCard(CardRequestDto cardRequestDto, CardList cardList, Long cardUserId) {
        return new Card(
                cardRequestDto.getCardTitle(),
                cardRequestDto.getCardContent(),
                cardRequestDto.getStartAt(),
                cardRequestDto.getEndAt(),
                cardRequestDto.getCardSequence(),
                cardUserId,
                cardList
        );
    }

    public void cardModify(CardModifyRequestDto requestDto, CardList cardList) {
        if (requestDto.getListId() != null) this.cardList = cardList;
        if (requestDto.getCardTitle() != null) this.cardTitle = requestDto.getCardTitle();
        if (requestDto.getCardContent() != null) this.cardContent = requestDto.getCardContent();
        if (requestDto.getStartAt() != null) this.startAt = requestDto.getStartAt();
        if (requestDto.getEndAt() != null) this.endAt = requestDto.getEndAt();
    }
}