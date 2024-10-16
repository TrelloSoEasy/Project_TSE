package com.sparta.tse.domain.List.repository;

import com.sparta.tse.domain.List.entity.CardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardListRepository extends JpaRepository<CardList, Long> {
}
