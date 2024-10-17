package com.sparta.tse.domain.comment.dto;

import com.sparta.tse.domain.user.dto.UserResponseDto;
import lombok.Data;

@Data
public class CommentSaveResponseDto {

    private final Long commentId;

    private final String content;

    private final UserResponseDto user;

    public CommentSaveResponseDto(Long commentId, String content, UserResponseDto user) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
    }

}
