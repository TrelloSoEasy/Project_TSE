package com.sparta.tse.domain.comment.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.comment.dto.CommentRequestDto;
import com.sparta.tse.domain.comment.dto.CommentResponseDto;
import com.sparta.tse.domain.comment.dto.CommentSaveResponseDto;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.comment.repository.CommentRepository;
import com.sparta.tse.domain.notification.dto.CommentAddedNotificationRequestDto;
import com.sparta.tse.domain.notification.service.NotificationService;
import com.sparta.tse.domain.notification.enums.EventType;
import com.sparta.tse.domain.user.dto.UserResponseDto;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public CommentResponseDto createComment(AuthUser authUser, CommentRequestDto commentRequestDto, Long cardId) {

        User user = userRepository.findById(authUser.getUserId()).orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));

        CardComment comment = new CardComment(
                commentRequestDto.getContent(),
                card,
                user
        );
        CardComment savedComment = commentRepository.save(comment);

        CommentAddedNotificationRequestDto commentAddedNotificationRequestDto = new CommentAddedNotificationRequestDto(
                EventType.COMMENT_ADDED,
                user.getNickname(),
                cardId,
                savedComment.getCommentId() // 새로 추가된 댓글 ID
        );

        notificationService.notifyCommentUpdated(commentAddedNotificationRequestDto);

        return new CommentResponseDto(
                savedComment.getCommentId(),
                savedComment.getContent(),
                new UserResponseDto(user.getUserId(), user.getEmail()),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public CommentSaveResponseDto updateComment(AuthUser authUser ,CommentRequestDto commentRequestDto, Long cardId, Long commentId) {
        Card card = cardRepository.findById(cardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));
//        CardComment comment = commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus.))

        CardComment comment = commentRepository.findByIdOrElseThrow(commentId);
        comment.commentUpdate(commentRequestDto.getContent());

        if(!comment.getUser().getUserId().equals(authUser.getUserId())) {
            throw new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER);
        }

        CardComment updateComment = commentRepository.save(comment);
        return new CommentSaveResponseDto(
                updateComment.getCommentId(),
                updateComment.getContent(),
                new UserResponseDto(updateComment.getUser().getUserId(), updateComment.getUser().getEmail())
        );
    }

    @Override
    public void deleteComment(AuthUser authUser ,Long cardId, Long commentId) {
        Card card = cardRepository.findByIdOrElseThrow(cardId);

        CardComment comment = commentRepository.findByIdOrElseThrow(commentId);

        if(!comment.getCard().getCardId().equals(card.getCardId())) {
            throw new ApiException(ErrorStatus._NOT_FOUND_COMMENT);
        }

        if(!comment.getUser().getUserId().equals(authUser.getUserId())) {
            throw new ApiException(ErrorStatus._BAD_REQUEST_NOT_FOUND_USER);
        }

        commentRepository.delete(comment);
    }

}
