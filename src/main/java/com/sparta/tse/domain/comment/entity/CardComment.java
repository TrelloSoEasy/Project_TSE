package com.sparta.tse.domain.comment.entity;

import com.sparta.tse.domain.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comments")
public class CardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;


    public CardComment() {

    }
}
