package com.sparta.tse.domain.board.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPostRequestDto {
    @NotNull
    private String title;

    private String backgroundColor;
}
