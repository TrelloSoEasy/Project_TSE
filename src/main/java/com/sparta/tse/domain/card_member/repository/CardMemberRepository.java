package com.sparta.tse.domain.card_member.repository;

import com.sparta.tse.domain.card_member.entity.CardMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardMemberRepository extends JpaRepository<CardMember, Long> {
}
