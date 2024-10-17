package com.sparta.tse.domain.List.entity;

import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "card_list")
@NoArgsConstructor
public class CardList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardListId; // 리스트 아이디

    private String title;

    private int sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "cardList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public CardList(Board board, String title, int sequence) {
        this.board = board;
        this.title = title;
        this.sequence = sequence;
    }

    // 평의 메서드: 카드 리스트에 카드를 추가
//    public void addCard(Card card) {
//        this.cards.add(card);
//        card.setCardList(this);
//    }

    public void updateList(Long cardListId, String title) {
        this.cardListId = cardListId;
        this.title = title;
    }

}
