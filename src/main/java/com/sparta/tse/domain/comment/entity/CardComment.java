package com.sparta.tse.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comments")
public class CardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
}
