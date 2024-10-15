package com.sparta.tse.domain.card.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.card.List.entity.CardList;
import com.sparta.tse.domain.card.List.repository.CardListRepository;
import com.sparta.tse.domain.card.dto.CardRequestDto;
import com.sparta.tse.domain.card.dto.CardResponseDto;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.repository.CardMemberRepository;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;

    private final CardListRepository cardListRepository;

    private final UserRepository userRepository;

    private final CardMemberRepository cardMemberRepository;

    @Transactional
    public CardResponseDto cardCreate(CardRequestDto requestDto) {

        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() ->
                        new ApiException(ErrorStatus._NOT_FOUND_LIST)
                );
        Card saveCard = cardRepository.save(Card.createCard(requestDto, savedList));

        for (Long assignedUserId : requestDto.userId) {
            User user = userRepository.findById(assignedUserId).orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));
            CardMember of = CardMember.of(user, saveCard);
            cardMemberRepository.save(of);
        }


        return CardResponseDto.of(saveCard);

    }

    public CardResponseDto cardRead(Long cardId) {

        Card savedCard = cardRepository.findById(cardId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_CARD)
        );



        return CardResponseDto.of(savedCard);

    }
}
