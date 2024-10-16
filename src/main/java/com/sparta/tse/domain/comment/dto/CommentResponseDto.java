package com.sparta.tse.domain.comment.dto;

import com.sparta.tse.domain.comment.entity.CardComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CommentResponseDto(Long commentId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto of(CardComment cardComment) {
        return new CommentResponseDto(
                cardComment.getCommentId(),
                cardComment.getContent(),
                cardComment.getCreatedAt(),
                cardComment.getUpdatedAt());
    }
}
