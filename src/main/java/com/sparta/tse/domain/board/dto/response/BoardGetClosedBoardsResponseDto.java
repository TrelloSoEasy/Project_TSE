package com.sparta.tse.domain.board.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardGetClosedBoardsResponseDto {
    private List<String> closedBoards;

    public BoardGetClosedBoardsResponseDto(List<String> closedBoards) {
        this.closedBoards = closedBoards;
    }
}
