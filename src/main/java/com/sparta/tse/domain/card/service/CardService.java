package com.sparta.tse.domain.card.service;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.List.repository.CardListRepository;
import com.sparta.tse.domain.card.dto.request.CardModifyRequestDto;
import com.sparta.tse.domain.card.dto.request.CardRequestDto;
import com.sparta.tse.domain.card.dto.response.CardResponseDto;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.repository.CardMemberRepository;
import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import com.sparta.tse.domain.file.repository.FileRepository;
import com.sparta.tse.domain.file.service.FileService;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
    private final FileService fileService;
    private final FileRepository fileRepository;


    @Transactional
    public CardResponseDto cardCreate(CardRequestDto requestDto, List<MultipartFile> file, AuthUser authUser) throws IOException {

        requestDto.cardSequence(cardRepository.cardSequenceMaxCount() + 1);

        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() ->
                        new ApiException(ErrorStatus._NOT_FOUND_LIST)
                );

        Card savedCard = cardRepository.save(Card.createCard(requestDto, savedList));

        for (Long assignedUserId : requestDto.userId) {
            User user = userRepository.findById(assignedUserId).orElseThrow(() ->
                    new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER)
            );
            CardMember of = CardMember.of(user, savedCard);
            cardMemberRepository.save(of);
        }

        if (file != null && !file.isEmpty()) {
            fileService.uploadFiles(savedCard.getCardId(), file, FileEnum.CARD);
        }
        List<File> image = getImages(savedCard);

        return CardResponseDto.of(savedCard, image);

    }

    public CardResponseDto cardRead(Long cardId, AuthUser user) {

        Card savedCard = cardRepository.findById(cardId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_CARD)
        );

        List<File> image = getImages(savedCard);

        return CardResponseDto.of(savedCard, image);

    }


    @Transactional
    public CardResponseDto cardModify(Long cardId, CardModifyRequestDto requestDto, List<MultipartFile> file, AuthUser authUser) throws IOException {
        // 카드 조회
        Card savedCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        // List 찾기
        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_LIST));

        // Card 객체로 CardMember 목록을 조회
        List<CardMember> existingMembers = cardMemberRepository.findAllByCard(savedCard);

        // 요청으로 들어온 userId 목록
        Set<Long> requestedUserIds = new HashSet<>(requestDto.getUserId());
        System.out.println("요청으로 들어온 userId 목록 = " + requestedUserIds.toString());

        // 현재 카드에 저장된 멤버의 userId 목록
        Set<Long> existingUserIds = existingMembers.stream()
                .map(cardMember -> cardMember.getUser().getUserId())
                .collect(Collectors.toSet());
        System.out.println("현재 카드에 저장된 멤버의 userId 목록 = " + existingUserIds.toString());


        // 삭제 될 멤버
        toDeletedMember(savedCard, requestedUserIds, existingUserIds);

        // 추가할 멤버
        addMember(savedCard, requestedUserIds, existingUserIds);


        savedCard.cardModify(requestDto, savedList);

        if (file != null && !file.isEmpty()) {
            fileService.uploadFiles(savedCard.getCardId(), file, FileEnum.CARD);
        }
        List<File> image = getImages(savedCard);

        return CardResponseDto.of(savedCard, image);
    }

    private User getUser(Long userIdToDelete) {
        User user = userRepository.findById(userIdToDelete)
                .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));
        return user;
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

    private List<File> getImages(Card savedCard) {
        List<File> image = fileRepository.findBySourceIdAndFileFolder(savedCard.getCardId(), FileEnum.CARD);
        return image;
    }

    private void addMember(Card savedCard, Set<Long> requestedUserIds, Set<Long> existingUserIds) {
        // 추가할 멤버 (요청에서 왔지만 기존에 없는 멤버)
        Set<Long> usersToAdd = new HashSet<>(requestedUserIds);
        usersToAdd.removeAll(existingUserIds);
        System.out.println("추가할 멤버 (요청에서 왔지만 기존에 없는 멤버) = " + usersToAdd.toString());

        // 추가할 멤버 처리
        for (Long userIdToAdd : usersToAdd) {
            User user = getUser(userIdToAdd);
            cardMemberRepository.save(CardMember.of(user, savedCard));
        }
    }

    private void toDeletedMember(Card savedCard, Set<Long> requestedUserIds, Set<Long> existingUserIds) {
        // 삭제할 멤버 (기존에 있지만 요청에 없는 멤버)
        Set<Long> usersToDelete = new HashSet<>(existingUserIds);
        usersToDelete.removeAll(requestedUserIds);
        System.out.println("삭제할 멤버 (기존에 있지만 요청에 없는 멤버) = " + usersToDelete.toString());

        // 삭제할 멤버 처리
        for (Long userIdToDelete : usersToDelete) {
            User user = getUser(userIdToDelete);
            CardMember cardMemberToDelete = cardMemberRepository.findByUserAndCard(user, savedCard)
                    .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_CARD_MEMBER));
            cardMemberRepository.delete(cardMemberToDelete);
        }
    }
}
