package com.sparta.tse.domain.emoji.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.comment.repository.CommentRepository;
import com.sparta.tse.domain.emoji.dto.EmojiRequestDto;
import com.sparta.tse.domain.emoji.dto.EmojiResponseDto;
import com.sparta.tse.domain.emoji.entity.Emoji;
import com.sparta.tse.domain.emoji.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmojiService {

    private final CommentRepository commentRepository;
    private final EmojiRepository emojiRepository;

    @Transactional
    public EmojiResponseDto createEmoji (EmojiRequestDto emojiRequestDto, Long commentId){

        CardComment comment = commentRepository.findById(commentId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_COMMENT));

        Emoji emoji = new Emoji(emojiRequestDto.getEmoji(), comment);

        List<Emoji> foundEmoji = emojiRepository.findByEmojiAndCardComment(emojiRequestDto.getEmoji(), comment);
        if(foundEmoji != null) {
            throw new ApiException(ErrorStatus._EMOJI_ALREADY_EXIST_);
        }

        Emoji savedEmoji = emojiRepository.save(emoji);

        return new EmojiResponseDto(
                savedEmoji.getEmoji(),
                savedEmoji.getCardComment().getCommentId()
        );
    }

    @Transactional
    public void deleteEmoji(Long commentId, Long emojiId) {

        // 1. 댓글이 존재하는지 확인
        CardComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_COMMENT));

        // 2. emojiId와 댓글로 해당 이모지를 조회
        Emoji emoji = emojiRepository.findByEmojiIdAndCardComment(emojiId, comment)
                .orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_EMOJI));

        // 3. 해당 이모지 삭제
        emojiRepository.delete(emoji);
    }

}
