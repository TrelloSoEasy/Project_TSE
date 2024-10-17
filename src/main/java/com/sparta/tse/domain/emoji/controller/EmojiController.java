package com.sparta.tse.domain.emoji.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.emoji.dto.EmojiRequestDto;
import com.sparta.tse.domain.emoji.dto.EmojiResponseDto;
import com.sparta.tse.domain.emoji.service.EmojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/comments")
public class EmojiController {

    private final EmojiService emojiService;

    @PostMapping("/{commentId}/emoji")
    public ApiResponse<EmojiResponseDto> createEmoji (@RequestBody EmojiRequestDto emojiRequestDto,
                                                      @PathVariable Long commentId) {
        return ApiResponse.createSuccess("이모지 생성 완료", HttpStatus.OK.value(), emojiService.createEmoji(emojiRequestDto, commentId));
    }

    @DeleteMapping("/{commentId}/emoji/{emojiId}")
    public ApiResponse<Void> deleteEmoji (@PathVariable Long commentId,
                                          @PathVariable Long emojiId) {
        emojiService.deleteEmoji(commentId, emojiId);
        return ApiResponse.createSuccess("이모지 삭제 완료", HttpStatus.OK.value(), null);
    }


}
