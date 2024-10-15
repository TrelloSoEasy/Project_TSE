package com.sparta.tse.domain.card.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.card.dto.CardRequestDto;
import com.sparta.tse.domain.card.dto.CardResponseDto;
import com.sparta.tse.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse")
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards")
    public ApiResponse<CardResponseDto> cardCreate(@RequestBody CardRequestDto requestDto) {
        return ApiResponse.createSuccess("카드 생성 완료",HttpStatus.OK.value(), cardService.cardCreate(requestDto));
    }

    @GetMapping("/cards/{cardId}")
    public ApiResponse<CardResponseDto> cardRead(@PathVariable Long cardId) {
        return ApiResponse.onSuccess(cardService.cardRead(cardId));
    }
}
