package com.sparta.tse.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardDto {
    private Long cardId;
    private String cardTitle;
    private Long cardSequence;

    public CardDto(Long cardId, String cardTitle, Long cardSequence) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardSequence = cardSequence;
    }
}
