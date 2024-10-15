package com.sparta.tse.domain.workspace_role_list.entity;

import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class WorkspaceRoleList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    private String role;
}
