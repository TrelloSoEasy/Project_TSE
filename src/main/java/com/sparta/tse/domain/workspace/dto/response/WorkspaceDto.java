package com.sparta.tse.domain.workspace.dto.response;

import com.sparta.tse.domain.workspace.entity.Workspace;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkspaceDto {
    private String workspaceName;
    private String workspaceDescription;

    public WorkspaceDto(Workspace workspace) {
        this.workspaceName = workspace.getName();
        this.workspaceDescription = workspace.getDescription();
    }
}
