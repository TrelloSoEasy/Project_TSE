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
import com.sparta.tse.domain.card.dto.response.CardSearchResponseDto;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.card_member.entity.CardMemberRole;
import com.sparta.tse.domain.card_member.repository.CardMemberRepository;
import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import com.sparta.tse.domain.file.repository.FileRepository;
import com.sparta.tse.domain.file.service.FileService;
import com.sparta.tse.domain.notification.dto.CardUpdatedNotificationRequestDto;
import com.sparta.tse.domain.notification.service.NotificationService;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sparta.tse.domain.notification.enums.EventType.CARD_UPDATED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final UserRepository userRepository;
    private final CardMemberRepository cardMemberRepository;
    private final NotificationService notificationService;
    private final FileService fileService;
    private final FileRepository fileRepository;


    @Transactional
    public CardResponseDto cardCreate(CardRequestDto requestDto, List<MultipartFile> file, AuthUser authUser) throws IOException {

        requestDto.cardSequence(cardRepository.cardSequenceMaxCount() + 1);

        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() ->
                        new ApiException(ErrorStatus._NOT_FOUND_LIST)
                );

        Card savedCard = cardRepository.save(Card.createCard(requestDto, savedList, authUser.getUserId()));

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

        if (cardMemberRepository.findByMemberIdAndCardId(user.getUserId(), cardId).isEmpty())
            throw new ApiException(ErrorStatus._NOT_FOUND_ROLE);

        Card savedCard = cardRepository.findById(cardId).orElseThrow(() ->
                new ApiException(ErrorStatus._NOT_FOUND_CARD)
        );

        List<File> image = getImages(savedCard);

        return CardResponseDto.of(savedCard, image);

    }


    @Transactional
    public CardResponseDto cardModify(CardModifyRequestDto requestDto, List<MultipartFile> file, AuthUser authUser) throws IOException {

        // 카드 조회
        Card savedCard = cardRepository.findById(requestDto.getCardId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));


        // Card 객체로 CardMember 목록을 조회
        List<CardMember> existingMembers = cardMemberRepository.findAllByCard(savedCard);


        CardMember matchingMember = existingMembers.stream()
                .filter(member -> member.getUser().getUserId().equals(authUser.getUserId()))
                .findFirst() // 일치하는 첫 번째 멤버만 찾음
                .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER)); // 없으면 예외 처리

        if (matchingMember.getRole().equals(CardMemberRole.USER))
            throw new ApiException(ErrorStatus._NOT_FOUND_ROLE);

        // 요청으로 들어온 userId 목록

        // 현재 카드에 저장된 멤버의 userId 목록
        Set<Long> existingUserIds = existingMembers.stream()
                .map(cardMember -> cardMember.getUser().getUserId())
                .collect(Collectors.toSet());


        if (requestDto.getUserId() != null) {
            Set<Long> requestedUserIds = new HashSet<>(requestDto.getUserId());

            if (!savedCard.getCardUserId().equals(authUser.getUserId()))
                throw new ApiException(ErrorStatus._NOT_FOUND_ROLE);

            // 삭제 될 멤버
            toDeletedMember(savedCard, requestedUserIds, existingUserIds);

            // 추가할 멤버
            addMember(savedCard, requestedUserIds, existingUserIds);
        }
        // List 찾기
        CardList savedList = cardListRepository.findById(requestDto.getListId())
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_LIST));

        savedCard.cardModify(requestDto, savedList);

        savedCard.cardModify(requestDto,savedList);


        if (file != null && !file.isEmpty()) {
            fileService.uploadFiles(savedCard.getCardId(), file, FileEnum.CARD);
        }
        List<File> image = getImages(savedCard);

        CardUpdatedNotificationRequestDto cardUpdatedNotificationRequestDto = new CardUpdatedNotificationRequestDto(
                CARD_UPDATED,
                authUser.getNickname(),
                savedCard.getCardId()
        );

        notificationService.notifyCardUpdated(cardUpdatedNotificationRequestDto);

        return CardResponseDto.of(savedCard, image);
    }



    private User getUser(Long userIdToDelete) {
        User user = userRepository.findById(userIdToDelete)
                .orElseThrow(() -> new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER));
        return user;
    }


    @Transactional
    public ApiResponse cardDeleted(Long cardId, AuthUser authUser) {

        // 카드 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
        cardMemberRepository.deleteByCard(card);

        if (!card.getCardUserId().equals(authUser.getUserId()))
            throw new ApiException(ErrorStatus._NOT_THE_AUTHOR);

        cardRepository.deleteById(cardId);
        return new ApiResponse("삭제 성공", HttpStatus.OK.value(), null);
    }

    public ApiResponse <Page<CardSearchResponseDto>> cardsSearch(String title,
                                                                 String content,
                                                                 LocalDateTime endAt,
                                                                 Long boardId,
                                                                 String assigneeName,
                                                                 int page,
                                                                 int size) {

        Pageable pageable = PageRequest.of(0,10);

        Page<Card> cards = cardRepository.searchCardsByTitleConTentDueDateAndBoardId(title,
                content, endAt, boardId, assigneeName, pageable);

        Page<CardSearchResponseDto> cardSearchResponseDtos = cards.map(card -> new CardSearchResponseDto(
                card.getCardId(), card.getCardTitle(), card.getCardContent(), card.getEndAt(), card.getCardMemberList() ));

        return ApiResponse.onSuccess(cardSearchResponseDtos);


    }


    private List<File> getImages(Card savedCard) {
        List<File> image = fileRepository.findBySourceIdAndFileFolder(savedCard.getCardId(), FileEnum.CARD);
        return image;
    }

    private void addMember(Card savedCard, Set<Long> requestedUserIds, Set<Long> existingUserIds) {
        // 추가할 멤버 (요청에서 왔지만 기존에 없는 멤버)
        Set<Long> usersToAdd = new HashSet<>(requestedUserIds);
        usersToAdd.removeAll(existingUserIds);

        // 추가할 멤버 처리
        for (Long userIdToAdd : usersToAdd) {
            User user = getUser(userIdToAdd);
            cardMemberRepository.save(CardMember.of(user, savedCard));

            CardUpdatedNotificationRequestDto cardUpdatedNotificationRequestDto = new CardUpdatedNotificationRequestDto(
                    CARD_UPDATED,
                    user.getNickname(),
                    savedCard.getCardId()
            );

            notificationService.notifyMemberAddedInCard(cardUpdatedNotificationRequestDto);

        }
    }

    private void toDeletedMember(Card savedCard, Set<Long> requestedUserIds, Set<Long> existingUserIds) {
        // 삭제할 멤버 (기존에 있지만 요청에 없는 멤버)
        Set<Long> usersToDelete = new HashSet<>(existingUserIds);
        usersToDelete.removeAll(requestedUserIds);

        // 삭제할 멤버 처리
        for (Long userIdToDelete : usersToDelete) {
            User user = getUser(userIdToDelete);
            CardMember cardMemberToDelete = cardMemberRepository.findByUserAndCard(user, savedCard)
                    .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD_MEMBER));
            cardMemberRepository.delete(cardMemberToDelete);
        }
    }

    @Transactional
    public ApiResponse cardUpMove(AuthUser authUser, Long cardId, Long sequenceNum) {

        Card savedCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        cardRepository.cardSequenceCountUp(savedCard.getCardSequence() -1 , sequenceNum);

        cardRepository.cardSequencUpModify(sequenceNum, savedCard.getCardId());

        return ApiResponse.createError("위치기가 변경되었습니다.", 200);

    }

    @Transactional
    public ApiResponse cardDownMove(AuthUser authUser, Long cardId, Long sequenceNum) {

        Card savedCard = cardRepository.findById(cardId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));


        cardRepository.cardSequenceCountDown(savedCard.getCardSequence() +1, sequenceNum);
        cardRepository.cardSequenceDownModify(sequenceNum, savedCard.getCardId());
        return null;
    }
}
















