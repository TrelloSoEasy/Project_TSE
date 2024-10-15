package com.sparta.tse.domain.workspaceRoleList.entity;


import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class workspaceRoleList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceRoleListId;

    @ManyToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    private String role;
}
