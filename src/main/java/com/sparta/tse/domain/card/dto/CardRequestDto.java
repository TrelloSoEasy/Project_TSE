package com.sparta.tse.domain.card.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CardRequestDto {

    public Long listId;
    public String cardTitle;
    public String cardContent;
    public LocalDateTime startAt;
    public LocalDateTime endAt;
    public List<Long> userId;
    public int cardSequence;

    public String cardStatus;
}
