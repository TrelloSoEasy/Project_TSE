package com.sparta.tse.domain.workspace.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkspaceGetResponseDto {
    private WorkspaceDto workspace;

    public WorkspaceGetResponseDto(WorkspaceDto workspace) {
        this.workspace = workspace;
    }
}
