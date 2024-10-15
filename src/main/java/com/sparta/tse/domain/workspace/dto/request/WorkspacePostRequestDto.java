package com.sparta.tse.domain.workspace.dto.request;

import lombok.Getter;

@Getter
public class WorkspacePostRequestDto {
    private String workspaceName;
    private String workspaceDescription;

    public WorkspacePostRequestDto(String workspaceName, String workspaceDescription) {
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
    }
}
