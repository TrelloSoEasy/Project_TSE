package com.sparta.tse.domain.card_member.entity;

import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.workspaceMember.entity.MemberRole;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Enumerated(EnumType.STRING)
    private CardMemberRole role;

    private CardMember(User user , Card card) {
        this.user = user;
        this.card = card;
        this.role = CardMemberRole.USER;
    }
    public static CardMember of(User user, Card card) {
        return new CardMember(user, card);
    }

}
