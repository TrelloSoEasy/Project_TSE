package com.sparta.tse.domain.List.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.dto.ListRequestDto;
import com.sparta.tse.domain.List.dto.ListResponseDto;
import com.sparta.tse.domain.List.dto.request.ListPostRequestDto;
import com.sparta.tse.domain.List.dto.ListUpdateRequestDto;
import com.sparta.tse.domain.List.dto.ListUpdateRequestDto;
import com.sparta.tse.domain.List.dto.ListUpdateRequestDto;
import com.sparta.tse.domain.List.dto.request.ListPostRequestDto;
import com.sparta.tse.domain.List.serivce.CardListService;
import com.sparta.tse.domain.List.serivce.CardListServiceImpl;
import com.sparta.tse.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/TSE/boards")
public class CardListController {

    private final CardListServiceImpl cardListService;

    @PostMapping("/{boardId}/list")
    public ApiResponse<ListResponseDto> createList(@RequestBody ListRequestDto listRequestDto,
                                                   @PathVariable Long boardId,
                                                   @AuthenticationPrincipal AuthUser authUser) {

        return ApiResponse.createSuccess("리스트 생성 완료", HttpStatus.OK.value(), cardListService.createList(listRequestDto, boardId,authUser));
    }

    @PutMapping("/{boardId}/lists/{listId}")
    public ApiResponse<ListResponseDto> updateList(@RequestBody ListUpdateRequestDto listUpdateRequestDto,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long listId) {
        return ApiResponse.createSuccess("리스트 수정 완료", HttpStatus.OK.value(), cardListService.updateList(listUpdateRequestDto, boardId, listId));
    }

    @DeleteMapping("/{boardId}/lits/{listId}")
    public ApiResponse<Void> deleteList (@PathVariable Long boardId,
                                         @PathVariable Long cardListId) {
        return ApiResponse.createSuccess("리스트 삭제 완료", HttpStatus.OK.value(), null);
    }
}
