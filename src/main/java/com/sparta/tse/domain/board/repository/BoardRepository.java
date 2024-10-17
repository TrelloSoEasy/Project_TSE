package com.sparta.tse.domain.board.repository;

import com.sparta.tse.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
