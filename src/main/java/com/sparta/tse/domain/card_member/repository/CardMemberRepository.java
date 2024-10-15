package com.sparta.tse.domain.card_member.repository;

import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardMemberRepository extends JpaRepository<CardMember, Long> {
    Optional<CardMember> findByUserAndCard(User user, Card savedCard);

    List<CardMember> findAllByCard(Card savedCard);

    void deleteByCard(Card card);
}
