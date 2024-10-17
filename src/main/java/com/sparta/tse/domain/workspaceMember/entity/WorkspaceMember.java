package com.sparta.tse.domain.workspaceMember.entity;

import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class WorkspaceMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public WorkspaceMember(Workspace workspace, User user, MemberRole role) {
        this.workspace = workspace;
        this.user = user;
        this.role = role;
    }

    public void updateMemberRole(MemberRole role) {
        this.role = role;
    }
}
