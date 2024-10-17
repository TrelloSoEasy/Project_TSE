package com.sparta.tse.domain.List.serivce;

import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.dto.ListRequestDto;
import com.sparta.tse.domain.List.dto.ListResponseDto;
import com.sparta.tse.domain.List.dto.ListUpdateRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;

import java.util.List;

public interface CardListService {

    // 리스트 생성
    ListResponseDto createList (ListRequestDto listRequestDto, Long boardId, AuthUser authUser);

    // 리스트 수정
    ListResponseDto updateList (ListUpdateRequestDto listUpdateRequestDto, Long boardId , Long cardListId);

    // 리스트 삭제
    void deleteList(Long boardId, Long cardListId);

}
