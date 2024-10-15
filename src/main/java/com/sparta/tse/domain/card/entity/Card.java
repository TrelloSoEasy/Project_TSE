package com.sparta.tse.domain.card.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.card.dto.CardRequestDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.image.entity.Images;
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
    private int cardSequence;

    @ManyToOne
    @JoinColumn(name = "card_list_id")
    private CardList cardList;

    @OneToMany
    private List<CardComment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card")
    private List<CardMember> cardMemberList = new ArrayList<>();

    @OneToMany
    private List<Images> imageList = new ArrayList<>();

    private Card(String cardTitle, String cardContent, LocalDateTime startAt, LocalDateTime endAt, int cardSequence, CardList cardList) {
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.startAt = startAt;
        this.endAt = endAt;
        this.cardSequence = cardSequence;
        this.cardList = cardList;
    }

    // 정적 팩토리 메서드
    public static Card createCard(CardRequestDto cardRequestDto, CardList cardList) {
        return new Card(
                cardRequestDto.cardTitle,
                cardRequestDto.cardContent,
                cardRequestDto.startAt,
                cardRequestDto.endAt,
                cardRequestDto.cardSequence,
                cardList
        );
    }
}