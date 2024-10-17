package com.sparta.tse.domain.List.repository;

import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.board.dto.response.ListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardListRepository extends JpaRepository<CardList, Long> {
    @Query("SELECT new com.sparta.tse.domain.board.dto.response.ListDto(l.cardListId,l.title,l.sequence) " +
            "FROM CardList l WHERE l.board.boardId = :boardId ORDER BY l.sequence")
    List<ListDto> findListsByBoardId(@Param("boardId") Long boardId);
}
