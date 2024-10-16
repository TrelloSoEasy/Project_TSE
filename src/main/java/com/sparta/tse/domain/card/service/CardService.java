package com.sparta.tse.domain.card.service;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.List.repository.CardListRepository;
import com.sparta.tse.domain.card.dto.request.CardModifyRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;
import com.sparta.tse.domain.card.dto.response.CardResponseDto;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.repository.CardMemberRepository;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Transactional
    public CardResponseDto cardModify(Long cardId, CardModifyRequestDto requestDto) {
        // 카드 조회
        Card findCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        // List 찾기
        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_LIST));

        // Card 객체로 CardMember 목록을 조회
        List<CardMember> existingMembers = cardMemberRepository.findAllByCard(findCard);

        // 요청으로 들어온 userId 목록
        Set<Long> requestedUserIds = new HashSet<>(requestDto.getUserId());

        // 현재 카드에 저장된 멤버의 userId 목록
        Set<Long> existingUserIds = existingMembers.stream()
                .map(cardMember -> cardMember.getUser().getUserId())
                .collect(Collectors.toSet());

        // 추가할 멤버 (요청에서 왔지만 기존에 없는 멤버)
        Set<Long> usersToAdd = new HashSet<>(requestedUserIds);
        usersToAdd.removeAll(existingUserIds);

        // 삭제할 멤버 (기존에 있지만 요청에 없는 멤버)
        Set<Long> usersToDelete = new HashSet<>(existingUserIds);
        usersToDelete.removeAll(requestedUserIds);

        // 삭제할 멤버 처리
        for (Long userIdToDelete : usersToDelete) {
            User user = userRepository.findById(userIdToDelete)
                    .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

            CardMember cardMemberToDelete = cardMemberRepository.findByUserAndCard(user, findCard)
                    .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_CARD_MEMBER));

            cardMemberRepository.delete(cardMemberToDelete);
        }

        // 추가할 멤버 처리
        for (Long userIdToAdd : usersToAdd) {
            User user = userRepository.findById(userIdToAdd)
                    .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));

            cardMemberRepository.save(CardMember.of(user, findCard));
        }

//        for (Long assignedUserId : requestDto.userId) {
//            User user = userRepository.findById(assignedUserId).orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));
//
//            // User와 Card를 기준으로 중복 여부 확인
//            if (!cardMemberRepository.findByUserAndCard(user, savedCard).isPresent()) {
//                cardMemberRepository.save(CardMember.of(user, savedCard));
//            }
//        }

        findCard.cardModify(requestDto, savedList);

        return CardResponseDto.of(findCard);
    }

    @Transactional
    public ApiResponse cardDeleted(Long cardId) {

        // 카드 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
        cardMemberRepository.deleteByCard(card);
        cardRepository.deleteById(cardId);
        return new ApiResponse("삭제 성공", HttpStatus.OK.value(), null);
    }
}
