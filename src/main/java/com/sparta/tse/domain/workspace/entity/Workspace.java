package com.sparta.tse.domain.workspace.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Workspace extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceId;

    @Column(length = 255,nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @OneToMany(mappedBy = "workspace",cascade = CascadeType.ALL)
    private List<Board> boards;

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
        //보드를 init 하는 생성자가 필요할 것 같습니다.
        this.boards = new ArrayList<>();
    }

    public Workspace(WorkspacePostRequestDto requestDto) {
        this.name = requestDto.getWorkspaceName();
        this.description = requestDto.getWorkspaceDescription();
    }

    public void update(WorkspaceUpdateRequestDto requestDto) {
        this.name= requestDto.getNewWorkspaceName();
        this.description= requestDto.getNewWorkspaceDescription();
    }
}
