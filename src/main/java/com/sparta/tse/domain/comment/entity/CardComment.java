package com.sparta.tse.domain.comment.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public CardComment(String content, Card card,User user) {
        this.content = content;
        this.card = card;
        this.user = user;
    }

    public void commentUpdate(String content) {
        this.content = content;
    }
}
