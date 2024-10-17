package com.sparta.tse.domain.card.repository;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {
    default Card findByIdOrElseThrow(Long cardId) {
        return findById(cardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
    }

    @Query("SELECT COUNT(c) FROM Card c")
    Long cardSequenceMaxCount();

    @Modifying
    @Query(value =
            "UPDATE Cards c " +
            "SET c.card_sequence = c.card_sequence + 1 " +
            "WHERE c.card_sequence BETWEEN :sequenceMovement AND :cardSequence"
            ,nativeQuery = true)
    void cardSequenceCountUp(@Param("cardSequence") Long cardSequence,
                             @Param("sequenceMovement") Long sequenceMovement);


    @Modifying
    @Query(value =
            "UPDATE Cards c " +
                    "SET c.card_sequence = :sequenceMovement " +
                    "WHERE c.card_id = :cardId"
            ,nativeQuery = true)
    void cardSequencUpModify(@Param("sequenceMovement") Long sequenceMovement,
                             @Param("cardId") Long cardId);





    @Modifying
    @Query(value =
            "UPDATE Cards c " +
            "SET c.card_sequence = c.card_sequence - 1 " +
            "WHERE c.card_sequence BETWEEN :sequenceMovement AND :sequenceNum"
            ,nativeQuery = true)
    void cardSequenceCountDown(@Param("sequenceMovement") long sequenceMovement,
                               @Param("sequenceNum") Long sequenceNum);

    @Modifying
    @Query(value =
            "UPDATE Cards c " +
                    "SET c.card_sequence = :sequenceNum " +
                    "WHERE c.card_id = :cardId"
            ,nativeQuery = true)
    void cardSequenceDownModify(@Param("sequenceNum") Long sequenceNum,
                                @Param("cardId") Long cardId);
}
