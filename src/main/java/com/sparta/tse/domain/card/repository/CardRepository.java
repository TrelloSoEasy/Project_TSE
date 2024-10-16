package com.sparta.tse.domain.card.repository;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    default Card findByIdOrElseThrow(Long cardId){
        return findById(cardId).orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }
}
