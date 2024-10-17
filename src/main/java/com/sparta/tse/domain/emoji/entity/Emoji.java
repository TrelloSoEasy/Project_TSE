package com.sparta.tse.domain.emoji.entity;

import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Comment;

@Entity
@Getter
@NoArgsConstructor
public class Emoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emoji_id")
    private Long emojiId;

    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardComment cardComment;

    public Emoji(String emoji, CardComment cardComment) {
        this.emoji = emoji;
        this.cardComment = cardComment;
    }
}
