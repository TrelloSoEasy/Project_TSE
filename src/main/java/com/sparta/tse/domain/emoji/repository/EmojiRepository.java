package com.sparta.tse.domain.emoji.repository;

import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.emoji.entity.Emoji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmojiRepository extends JpaRepository<Emoji, Long> {


    Optional<Emoji> findByEmojiIdAndCardComment(Long emojiId, CardComment comment);

    List<Emoji> findByEmojiAndCardComment(String emoji, CardComment comment);

}
