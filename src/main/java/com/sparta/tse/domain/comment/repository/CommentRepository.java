package com.sparta.tse.domain.comment.repository;

import com.sparta.tse.domain.comment.entity.CardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public interface CommentRepository extends JpaRepository<CardComment, Long> {
    default CardComment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(()-> new NoSuchElementException("댓글을 찾을 수 없습니다"));
    }
}
