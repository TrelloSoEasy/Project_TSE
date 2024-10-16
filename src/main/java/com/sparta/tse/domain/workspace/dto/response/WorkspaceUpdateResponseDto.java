package com.sparta.tse.domain.workspace.dto.response;

import lombok.Getter;

@Getter
public class WorkspaceUpdateResponseDto {
    private final String updatedWorkspaceDescription;

    public WorkspaceUpdateResponseDto(String updatedWorkspaceDescription) {
        this.updatedWorkspaceDescription = updatedWorkspaceDescription;
    }
}
