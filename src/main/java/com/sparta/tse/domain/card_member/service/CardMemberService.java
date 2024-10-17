package com.sparta.tse.domain.card_member.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.card_member.dto.CardMemberResponseDto;
import com.sparta.tse.domain.card_member.dto.CardMemberRoleModifyRequestDto;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.repository.CardMemberRepository;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardMemberService {

    private final CardMemberRepository cardMemberRepository;
    private final CardRepository cardRepository;

    @Transactional
    public CardMemberResponseDto cardMemberRoleModify(AuthUser user, CardMemberRoleModifyRequestDto requestDto) {

        Card savedCard = cardRepository.findById(requestDto.getCardId()).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_CARD)
        );

        if (!savedCard.getCardUserId().equals(user.getUserId())) {
            throw new ApiException(ErrorStatus._NOT_THE_AUTHOR);
        }
        CardMember savedCardMember = cardMemberRepository.findByMemberIdAndCardId(requestDto.getMemberId(), savedCard.getCardId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD_MEMBER));

        savedCardMember.cardMemberRoleModify(requestDto.getCardMemberRole());

        return CardMemberResponseDto.of(savedCardMember);

    }
}
