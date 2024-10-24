package com.sparta.tse.domain.card.dto.response;

import com.sparta.tse.domain.card_member.dto.CardMemberResponseDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CardSearchResponseDto {

    private final Long cardId;
    private final String cardTitle;
    private final String cardContent;
    private final LocalDateTime endAt;
    private  final List<CardMemberResponseDto> assigneeName;

    public CardSearchResponseDto(Long cardId, String cardTitle, String cardContent, LocalDateTime endAt, List<CardMember> assigneeName) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.cardContent = cardContent;
        this.endAt = endAt;
        this.assigneeName = assigneeName.stream().map(CardMemberResponseDto::of).collect(Collectors.toList());
    }
}
