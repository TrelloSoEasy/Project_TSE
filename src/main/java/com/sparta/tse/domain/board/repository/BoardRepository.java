package com.sparta.tse.domain.board.repository;

import com.sparta.tse.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //보드 들중 특정 workspaceId 인 삭제된 보드들만 리턴받는 쿼리
    @Query("SELECT b from Board b where b.workspace.workspaceId= :workspaceId and b.isClosed=true")
    List<Board> findByWorkspaceIdAndIsClosed(@Param("workspaceId") Long workspaceId);
}
