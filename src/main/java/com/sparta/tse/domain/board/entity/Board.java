package com.sparta.tse.domain.board.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.List.entity.CardList;
import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BoardId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 20)
    private String backGroundColor;

    @Column(length = 20)
    private String image;

    @Enumerated(EnumType.STRING)
    private Boardenum bookmark;

    private boolean isDeleted;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CardList> lists;

    @ManyToOne
    @JoinColumn(name= "workspace_id")
    private Workspace workspace;

    public Board(String title,Workspace workspace) {
        this.title = title;
        this.bookmark = Boardenum.OFF;
        this.isDeleted = false;
        this.workspace = workspace;
        this.lists = new ArrayList<>();
        //리스트를 init하는 생성자가 필요할 거 같습니다.
    }

    public void addBackgroundColor (String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public void addImage(String image) {
        this.image = image;
    }
}

