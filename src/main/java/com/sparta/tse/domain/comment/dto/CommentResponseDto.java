package com.sparta.tse.domain.comment.dto;

import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String content;
    private final UserResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CommentResponseDto(Long commentId, String content, UserResponseDto user ,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CommentResponseDto of(CardComment cardComment) {
        return new CommentResponseDto(
                cardComment.getCommentId(),
                cardComment.getContent(),
                new UserResponseDto(cardComment.getCommentId(), cardComment.getContent()),
                cardComment.getCreatedAt(),
                cardComment.getUpdatedAt());
    }
}
