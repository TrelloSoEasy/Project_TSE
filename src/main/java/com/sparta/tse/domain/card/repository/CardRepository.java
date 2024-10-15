package com.sparta.tse.domain.card.repository;

import com.sparta.tse.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
