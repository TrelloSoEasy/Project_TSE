package com.sparta.tse.domain.comment.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long cardId) {

        Card card = cardRepository.findById(cardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        CardComment comment = new CardComment(
                commentRequestDto.getContent(),
                card
        );
        CardComment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long cardId, Long commentId) {
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));
//        CardComment comment = commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus.))

        CardComment comment = commentRepository.findByIdOrElseThrow(commentId);
        comment.commentUpdate(commentRequestDto);

        // 인증 검사 맞지 조건문으로 카드가 없으면 예외를 던지기
//        if(!card.getCardList().)
        return CommentResponseDto.of(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long cardId, Long commentId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        CardComment comment = commentRepository.findByIdOrElseThrow(commentId);

        commentRepository.delete(comment);
    }

}
