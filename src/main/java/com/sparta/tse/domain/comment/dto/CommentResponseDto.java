package com.sparta.tse.domain.comment.dto;

import com.sparta.tse.domain.comment.entity.CardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;

    public static CommentResponseDto of(CardComment comment) {
        return new CommentResponseDto(comment.getCommentId(), comment.getContent());
    }

    private CommentResponseDto(Long commentId, String content) {
        this.commentId =commentId;
        this.content = content;
    }
}
