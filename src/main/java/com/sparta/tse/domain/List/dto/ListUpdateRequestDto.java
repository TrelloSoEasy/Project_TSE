package com.sparta.tse.domain.List.dto;

import lombok.Data;

@Data
public class ListUpdateRequestDto {

    private Long cardListId;

    private String title;

    private int sequence;

}
