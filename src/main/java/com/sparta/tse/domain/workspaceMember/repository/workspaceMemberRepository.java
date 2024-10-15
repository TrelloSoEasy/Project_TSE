package com.sparta.tse.domain.workspaceMember.repository;

import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface workspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
}
