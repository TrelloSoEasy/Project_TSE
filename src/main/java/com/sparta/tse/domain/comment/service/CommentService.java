package com.sparta.tse.domain.comment.service;

import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.dto.CommentSaveResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {

    // 댓글 등록
    CommentResponseDto createComment(AuthUser authUser, CommentRequestDto commentRequestDto, Long cardId);

    // 댓글 수정
    CommentSaveResponseDto updateComment(AuthUser authUSer , CommentRequestDto commentRequestDto, Long cardId, Long commentId);

    // 댓글 삭제
    void deleteComment(AuthUser authuser ,Long cardId, Long commentId);
}
