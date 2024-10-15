package com.sparta.tse.domain.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceUpdateRequestDto {
    private Long id;
    private String newWorkspaceName;
    private String newWorkspaceDescription;
}
