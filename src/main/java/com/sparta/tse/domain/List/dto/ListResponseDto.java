package com.sparta.tse.domain.List.dto;

import lombok.Data;

@Data
public class ListResponseDto {

    private final Long cardListId;

    private final String title;

    private final int sequence;

    public ListResponseDto(Long cardListId, String title, int sequence) {
        this.cardListId = cardListId;
        this.title = title;
        this.sequence = sequence;
    }

}
