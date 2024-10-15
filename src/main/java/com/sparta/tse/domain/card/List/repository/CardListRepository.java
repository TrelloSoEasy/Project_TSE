package com.sparta.tse.domain.card.List.repository;

import com.sparta.tse.domain.card.List.entity.CardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardListRepository extends JpaRepository<CardList, Long> {
}
