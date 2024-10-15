package com.sparta.tse.domain.invitation.repository;


import com.sparta.tse.domain.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
