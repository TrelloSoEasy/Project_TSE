package com.sparta.tse.domain.workspaceMember.repository;



import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspaceMember.entity.MemberRole;
import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    @Query("SELECT wm.workspace from WorkspaceMember wm where wm.user.userId= :userId")
    List<Workspace> findWorkspaceByUserId(@Param("userId") Long userId);

    @Query("SELECT wm.role from WorkspaceMember wm where wm.user.userId= :userId and wm.workspace.workspaceId= :workspaceId")
    Optional<String> findRoleByEmail(@Param("userId") Long userId, @Param("workspaceId") Long workspaceId);
}
