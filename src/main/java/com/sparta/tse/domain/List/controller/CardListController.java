package com.sparta.tse.domain.List.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.dto.ListRequestDto;
import com.sparta.tse.domain.List.dto.ListResponseDto;
import com.sparta.tse.domain.List.serivce.CardListService;
import com.sparta.tse.domain.List.serivce.CardListServiceImpl;
import com.sparta.tse.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tes/boards")
public class CardListController {

    private final CardListServiceImpl cardListService;

    @PostMapping("/{boardId}/lists/{userId}")
    public ApiResponse<ListResponseDto> createList(@RequestBody ListRequestDto listRequestDto,
                                                   @PathVariable Long boardId) {

        return ApiResponse.createSuccess("리스트 생성 완료", HttpStatus.OK.value(), cardListService.createList(listRequestDto, boardId));
    }

    @PutMapping("/{boardId}/lists/{listId}")
    public ApiResponse<ListResponseDto> updateList(@RequestBody ListRequestDto listRequestDto,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long listId) {
        return ApiResponse.createSuccess("리스트 수정 완료", HttpStatus.OK.value(), cardListService.updateList(listRequestDto, boardId, listId));
    }

    @DeleteMapping("/{boardId}/lits/{listId}")
    public ApiResponse<Void> deleteList (@PathVariable Long boardId,
                                         @PathVariable Long cardListId) {
        return ApiResponse.createSuccess("리스트 삭제 완료", HttpStatus.OK.value(), null);
    }
}
