package com.sparta.tse.domain.workspace.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkspaceDeleteRequestDto {
    private String deleteCode;

    public WorkspaceDeleteRequestDto(String deleteCode) {
        this.deleteCode = deleteCode;
    }
}
