package com.sparta.tse.domain.board.entity;

import com.sparta.tse.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 20)
    private String backGroundColor;

    @Column
    private Boardenum bookmark;

//    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<List> lists = new ArrayList<>();

    public Board(String title, String backGroundColor, Boardenum bookmark) {
        this.title = title;
        this.backGroundColor = backGroundColor;
        this.bookmark = bookmark;
        //리스트를 init하는 생성자가 필요할 거 같습니다.
    }
}

