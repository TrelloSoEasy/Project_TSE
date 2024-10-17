package com.sparta.tse.domain.board.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ListDto {
    private Long listId;
    private List<CardDto> cards;
    private Integer ListSequence;

    public ListDto(Long listId, Integer ListSequence) {
        this.listId = listId;
        this.ListSequence = ListSequence;
    }

    public void addCard(List<CardDto> cards) {
        this.cards=cards;
    }

}
