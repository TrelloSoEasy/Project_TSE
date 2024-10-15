package com.sparta.tse.domain.workspace;

import com.sparta.tse.domain.workspace.dto.request.WorkspacePostRequestDto;
import com.sparta.tse.domain.workspace.dto.response.WorkspacePostResponseDto;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import com.sparta.tse.domain.workspace.service.WorkspaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class workspacetest {

    @InjectMocks
    private WorkspaceService workspaceService;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Test
    public void 워크스페이스_포스트_성공() {
        Long workspaceId = 1L;
        WorkspacePostRequestDto requestDto = new WorkspacePostRequestDto(
                "workspaceTestname"
                ,"workspaceTestdescription");
        Workspace workspace = new Workspace(requestDto);
        ReflectionTestUtils.setField(workspace, "workspaceId", workspaceId);
        given(workspaceRepository.save(any(Workspace.class))).willReturn(workspace);

        WorkspacePostResponseDto responseDto = workspaceService.postWorkspace(requestDto);

        assertEquals(responseDto.getId(), workspaceId);
        assertEquals(responseDto.getWorkspaceName(), requestDto.getWorkspaceName());
        assertEquals(responseDto.getWorkspaceDescription(), requestDto.getWorkspaceDescription());


    }
}
