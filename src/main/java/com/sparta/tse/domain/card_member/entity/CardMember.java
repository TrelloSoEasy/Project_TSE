package com.sparta.tse.domain.card_member.entity;

import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_member")
@NoArgsConstructor
@Getter
public class CardMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardMemberId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Card card;
}
