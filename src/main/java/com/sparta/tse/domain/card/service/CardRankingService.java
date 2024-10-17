package com.sparta.tse.domain.card.service;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardRankingService {


    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Autowired
    private CardRepository cardRepository; // 카드 정보 조회를 위한 레포지토리

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    public void resetCardViews() {
        // 모든 카드의 조회수를 초기화
        List<Long> allCardIds = getAllCardIds(); // 모든 카드 ID를 가져오는 메서드
        for (Long cardId : allCardIds) {
            redisTemplate.delete("card:" + cardId + "views"); // 조회수 초기화(삭제)
        }
    }

    private List<Long> getAllCardIds() {
        // 카드 ID를 가져오는 로직 구현
        return cardRepository.findAll().stream() // 모든 카드 정보를 가져옴
                .map(Card::getCardId) // 카드 객체에서 ID를 추출
                .collect(Collectors.toList()); // List<Long>으로 변환
    }
}