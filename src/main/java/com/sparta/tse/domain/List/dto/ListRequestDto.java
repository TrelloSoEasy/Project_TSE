package com.sparta.tse.domain.List.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListRequestDto {
    private Long cardListId;
    private String title;
    private int sequence;

}
