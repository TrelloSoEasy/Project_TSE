package com.sparta.tse.domain.card.controller;

import com.sparta.tse.domain.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
}
