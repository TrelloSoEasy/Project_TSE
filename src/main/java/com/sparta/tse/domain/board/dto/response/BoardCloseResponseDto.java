package com.sparta.tse.domain.board.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCloseResponseDto {

    private Long boardId;

    public BoardCloseResponseDto(Long boardId) {
        this.boardId = boardId;
    }
}
