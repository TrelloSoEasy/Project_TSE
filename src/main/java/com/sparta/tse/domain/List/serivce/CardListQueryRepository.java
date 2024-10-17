package com.sparta.tse.domain.List.serivce;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.tse.domain.List.entity.QCardList;
import com.sparta.tse.domain.board.dto.response.BoardGetResponseDto;
import com.sparta.tse.domain.board.dto.response.CardDto;
import com.sparta.tse.domain.board.dto.response.ListDto;
import com.sparta.tse.domain.card.entity.QCard;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardListQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QCardList cardList=QCardList.cardList;
    private final QCard card=QCard.card;

    public List<ListDto> findListsByBoardId(Pageable pageable, Long boardId) {
        return queryFactory.select(
                Projections.constructor(
                        ListDto.class,
                        cardList.cardListId,
                        Projections.constructor(CardDto.class,card.cardId,card.cardTitle,card.cardSequence),
                        cardList.sequence
                )
                )
                .from(cardList).
                leftJoin(cardList.cards,card)
                .where(
                        boardId(boardId)
                ).orderBy(cardList.sequence.asc(),card.cardSequence.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }
    private BooleanExpression boardId(Long boardId) {
        return cardList.board.BoardId.eq(boardId);
    }
}
