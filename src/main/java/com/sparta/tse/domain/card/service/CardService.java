package com.sparta.tse.domain.card.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.List.repository.CardListRepository;
import com.sparta.tse.domain.card.dto.CardRequestDto;
import com.sparta.tse.domain.card.dto.CardResponseDto;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;

    private final CardListRepository cardListRepository;

    @Transactional
    public CardResponseDto cardCreate(CardRequestDto requestDto) {

        CardList byId = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() ->
                        new ApiException(ErrorStatus._BAD_REQUEST_NOT_LIST)
                );

        Card save = cardRepository.save(Card.createCard(requestDto, byId));

        return CardResponseDto.of(
                save.getCardList().getCardListId(),
                save.getCardId(),
                save.getCardTitle(),
                save.getCardContent(),
                save.getCardSequence(),
                save.getStartAt(),
                save.getEndAt(),
                save.getCommentList(),
                save.getCardMemberList(),
                save.getImageList());

    }

}
