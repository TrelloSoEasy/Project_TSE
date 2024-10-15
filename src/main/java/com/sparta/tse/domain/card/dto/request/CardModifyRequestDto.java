package com.sparta.tse.domain.card.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardModifyRequestDto {
    public Long listId;
    public String cardTitle;
    public String cardContent;
    public LocalDateTime startAt;
    public LocalDateTime endAt;
    public List<Long> userId;
    public int cardSequence;
}
