package com.sparta.tse.domain.emoji.dto;

import com.sparta.tse.domain.comment.entity.CardComment;
import lombok.Getter;

@Getter
public class EmojiResponseDto {

    private final String emoji;

    private final Long commentId;

    public EmojiResponseDto(String emoji, Long commentId) {
        this.emoji = emoji;
        this.commentId = commentId;
    }

}
