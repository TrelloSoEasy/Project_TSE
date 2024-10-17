package com.sparta.tse.domain.List.serivce;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.dto.ListRequestDto;
import com.sparta.tse.domain.List.dto.ListResponseDto;
import com.sparta.tse.domain.List.dto.ListUpdateRequestDto;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.List.repository.CardListRepository;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.board.repository.BoardRepository;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sparta.tse.domain.user.enums.UserRole.USER_ROLE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardListServiceImpl implements CardListService{

    private final UserRepository userRepository;
    private final CardListRepository cardListRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public ListResponseDto createList(ListRequestDto listRequestDto, Long boardId, AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                ()-> new ApiException(ErrorStatus._NOT_FOUND_USER));
        // 보드가 존재하는지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_BOARD));

//        // 읽기 전용인지 확인
//        try{
//            User user = userRepository.findById(userId)
//                    .orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_USER));
//            if(user.getUserId().equals(USER_ROLE)) {
//                throw new ApiException(ErrorStatus._BAD_REQUEST_USER);
//            }
//        } catch (Exception e) {
//            return new ListResponseDto(null, "권한이 없습니다", 0);
//        }
//
//        // 리스트 순서 업데이트
//        CardList list;
//        if(listRequestDto.getSequence() != 0) {
//            list = cardListRepository.findById(listRequestDto.getListId())
//                    .orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_LIST));
//
//            list.setSequence(listRequestDto.getSequence());
//        } else {
//            // 새 리스트 생성
//
//
//            );
//        }
// 보드에 카드를 만드는 로직
        CardList list = new CardList(
                board,
                listRequestDto.getTitle(),
                listRequestDto.getSequence());

        CardList savedList = cardListRepository.save(list);
        return new ListResponseDto(
                savedList.getCardListId(),
                savedList.getTitle(),
                savedList.getSequence()
        );
    }



    @Override
    @Transactional
    public ListResponseDto updateList (ListUpdateRequestDto listUpdateRequestDto, Long boardId, Long cardListId) {

        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ApiException(ErrorStatus._BAD_REQUEST_NOT_BOARD));

        CardList cardList = cardListRepository.findById(cardListId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        if(!cardList.getBoard().getBoardId().equals(boardId)) {
            throw new ApiException(ErrorStatus._NOT_FOUND_EMAIL);
        }

        cardList.updateList(
                listUpdateRequestDto.getCardListId(),
                listUpdateRequestDto.getTitle()
        );

        // 리스트 저장
        CardList savedList = cardListRepository.save(cardList);
        return new ListResponseDto(
                savedList.getCardListId(),
                savedList.getTitle(),
                savedList.getSequence()
        );
    }

    @Override
    @Transactional
    public void deleteList(Long boardId, Long cardListId) {

        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_BOARD));

        CardList cardList = cardListRepository.findById(cardListId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        cardListRepository.save(cardList);
    }


}
