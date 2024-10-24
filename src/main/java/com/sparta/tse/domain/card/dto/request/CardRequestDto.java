package com.sparta.tse.domain.card.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardRequestDto {

    public Long listId;
    public String cardTitle;
    public String cardContent;
    public LocalDateTime startAt;
    public LocalDateTime endAt;
    public List<Long> userId;
    public Long cardSequence;

    public void cardSequence(Long maxSequence) {
        this.cardSequence = maxSequence;
    }

    public CardRequestDto (Long listId, String cardTitle, String cardContent, LocalDateTime startAt,
                           LocalDateTime endAt, List<Long> userId, Long cardSequence) {
        this.listId = listId;
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.startAt = startAt;
        this.endAt = endAt;
        this.userId = userId;
        this.cardSequence = cardSequence;
    }
}
