package com.sparta.tse.domain.card.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.card.dto.request.CardModifyRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;
import com.sparta.tse.domain.card.dto.response.CardResponseDto;
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

    @PatchMapping("/cards/{cardId}")
    public ApiResponse<CardResponseDto> cardModify(@PathVariable Long cardId, @RequestBody CardModifyRequestDto requestDto) {
//        NotificationRequestDto requestDto = new NotificationRequestDto("CARD_UPDATED", nickname);
//        notificationService.notifiyMemberAdded(requestDto);
        return ApiResponse.createSuccess("수정이 완료 되었습니다.", HttpStatus.OK.value(), cardService.cardModify(cardId, requestDto));
    }

    @DeleteMapping("/cards/{cardId}")
    public ApiResponse cardDeleted(@PathVariable Long cardId) {
        return cardService.cardDeleted(cardId);
    }
}
