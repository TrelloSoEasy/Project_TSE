package com.sparta.tse.domain.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspacePostResponseDto {
    private final Long id;
    private final String workspaceName;
    private final String workspaceDescription;
}
