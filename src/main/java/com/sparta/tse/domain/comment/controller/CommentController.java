package com.sparta.tse.domain.comment.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse/cards")
public class CommentController {

    private final CommentServiceImpl commentService;

    // 등록
    @PostMapping("/{cardId}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                                         @PathVariable Long cardId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.createError("댓글 생성 완료", 200));
    }

    @PutMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                                                         @PathVariable Long cardId,
                                                                         @PathVariable Long commentId) {
//        return ResponseEntity.status(HttpStatus.)
        try {
            CommentResponseDto updateComment = commentService.updateComment(commentRequestDto, cardId, commentId);
            return ResponseEntity.ok(ApiResponse.onSuccess(updateComment));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError("댓글 수정 실패", 401));
        }
    }

    @DeleteMapping("/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long cardId,
                                                           @PathVariable Long commentId) {
        try {
            commentService.deleteComment(cardId, commentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.createError("댓글 삭제 실패", 401));
        }
    }

}
