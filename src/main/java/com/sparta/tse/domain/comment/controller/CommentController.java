package com.sparta.tse.domain.comment.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.dto.CommentSaveResponseDto;
import com.sparta.tse.domain.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse/cards")
public class CommentController {

    private final CommentServiceImpl commentService;

    // auth 로그인할떄 받은 회원에 관한 토큰
    // 등록
    @PostMapping("/{cardId}/comments") // 수정
    public ApiResponse<CommentResponseDto> createComment(@AuthenticationPrincipal AuthUser authUser,
                                                         @RequestBody CommentRequestDto commentRequestDto,
                                                         @PathVariable Long cardId) {

        return ApiResponse.createSuccess("댓글 생성 완료", HttpStatus.OK.value(), commentService.createComment(authUser, commentRequestDto, cardId));
    }

    @PutMapping("/{cardId}/comments/{commentId}")
    public ApiResponse<CommentSaveResponseDto> updateComment(@AuthenticationPrincipal AuthUser authUser,
                                                             @RequestBody CommentRequestDto commentRequestDto,
                                                             @PathVariable Long cardId,
                                                             @PathVariable Long commentId) {
//        return ResponseEntity.status(HttpStatus.)
        return ApiResponse.createSuccess("댓글 수정 완료", HttpStatus.OK.value(),
                commentService.updateComment(authUser, commentRequestDto, cardId, commentId));

    }

    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ApiResponse<Void> deleteComment(@AuthenticationPrincipal AuthUser authUser,
                                           @PathVariable Long cardId,
                                           @PathVariable Long commentId) {

        commentService.deleteComment(authUser, cardId, commentId);
        return ApiResponse.createSuccess("댓글 삭제 완료", HttpStatus.OK.value(), null);
    }
}
