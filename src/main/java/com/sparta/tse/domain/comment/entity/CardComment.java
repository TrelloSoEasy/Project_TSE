package com.sparta.tse.domain.comment.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class CardComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;


    public CardComment(String content, Card card) {
        this.content = content;
        this.card = card;
    }

    public void commentUpdate(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
