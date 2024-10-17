package com.sparta.tse.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardPostResponseDto {
    private String boardName;
    private String boardBackgroundColor;
    private String image;

    public BoardPostResponseDto(String boardName) {
        this.boardName = boardName;
    }

    public void addImage(String image) {
        this.image = image;
    }

    public void addBoardBackgroundColor(String boardBackgroundColor) {
        this.boardBackgroundColor = boardBackgroundColor;
    }
}
