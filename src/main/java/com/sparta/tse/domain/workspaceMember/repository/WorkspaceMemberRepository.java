package com.sparta.tse.domain.workspaceMember.repository;

import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    @Query("SELECT wm.workspace from WorkspaceMember wm where wm.user.userId= :userId")
    List<Workspace> findWorkspaceByUserId(@Param("userId") Long userId);
}
