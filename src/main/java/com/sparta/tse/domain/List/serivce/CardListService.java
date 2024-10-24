package com.sparta.tse.domain.List.serivce;

import com.sparta.tse.domain.List.dto.ListRequestDto;
import com.sparta.tse.domain.List.dto.ListResponseDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;

public interface CardListService {

    // 리스트 생성
    ListResponseDto createList (ListRequestDto listRequestDto, Long boardId);

    // 리스트 수정
    ListResponseDto updateList (ListRequestDto listRequestDto,Long boardId ,Long cardListId);

    // 리스트 삭제
    void deleteList(Long workspacesId, Long cardListId);

}
