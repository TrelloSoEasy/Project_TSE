package com.sparta.tse.domain.card.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card.dto.request.CardModifyRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;
import com.sparta.tse.domain.card.dto.response.CardResponseDto;
import com.sparta.tse.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse")
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards")
    public ApiResponse<CardResponseDto> cardCreate(@AuthenticationPrincipal AuthUser user,
                                                   @RequestPart CardRequestDto requestDto,
                                                   @RequestPart(value = "file", required = false) List<MultipartFile> file) throws IOException {
        return ApiResponse.createSuccess("카드 생성 완료", HttpStatus.OK.value(), cardService.cardCreate(requestDto, file, user));
    }

    @GetMapping("/cards/{cardId}")
    public ApiResponse<CardResponseDto> cardRead(@AuthenticationPrincipal AuthUser user, @PathVariable Long cardId) {
        return ApiResponse.onSuccess(cardService.cardRead(cardId, user));
    }

    @PatchMapping("/cards")
    public ApiResponse<CardResponseDto> cardModify(@AuthenticationPrincipal AuthUser user,
                                                   @RequestPart CardModifyRequestDto requestDto,
                                                   @RequestPart(value = "file", required = false) List<MultipartFile> file
    ) throws IOException {
        return ApiResponse.createSuccess("수정이 완료 되었습니다.", HttpStatus.OK.value(), cardService.cardModify(requestDto, file, user));
    }

    @DeleteMapping("/cards/{cardId}")
    public ApiResponse cardDeleted(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long cardId) {
        return cardService.cardDeleted(cardId, authUser);
    }

    @PatchMapping("/cards/{cardId}/sequence/{sequenceNum}/up")
    public ApiResponse cardUpMove(@AuthenticationPrincipal AuthUser authUser,
                                  @PathVariable Long cardId,
                                  @PathVariable Long sequenceNum) {
        return cardService.cardUpMove(authUser, cardId, sequenceNum);
    }

    @PatchMapping("/cards/{cardId}/sequence/{sequenceNum}/down")
    public ApiResponse cardDownMove(@AuthenticationPrincipal AuthUser authUser,
                                    @PathVariable Long cardId,
                                    @PathVariable Long sequenceNum) {
        return cardService.cardDownMove(authUser, cardId, sequenceNum);
    }
}
