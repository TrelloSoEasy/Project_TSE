package com.sparta.tse.domain.workspaceMember.entity;

import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class workspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
    private String role;

    public workspaceMember(Workspace workspace, String role/* ,User user*/) {
        this.workspace = workspace;
        //this.user = user;
        this.role = role;
    }
}
