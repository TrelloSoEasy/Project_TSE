package com.sparta.tse.domain.workspace.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.request.WorkspaceUpdateRequestDto;
import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "workspace",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "workspace",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<WorkspaceMember> members;

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
        //보드를 init 하는 생성자가 필요할 것 같습니다.
        this.boards = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addMember(WorkspaceMember workspaceMember) {
        members.add(workspaceMember);
    }

    public void addBoard(Board board) {
        boards.add(board);
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
