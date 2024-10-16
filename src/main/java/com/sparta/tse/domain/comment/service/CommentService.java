package com.sparta.tse.domain.comment.service;

import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {

    // 댓글 등록
    CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long cardId);

    // 댓글 수정
    CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long cardId, Long commentId);

    // 댓글 삭제
    void deleteComment(Long cardId, Long commentId);
}
