package com.sparta.tse.domain.board.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.board.dto.request.BoardPostRequestDto;
import com.sparta.tse.domain.board.dto.response.BoardPostResponseDto;
import com.sparta.tse.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TSE")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{workspaceId}/boards")
    public ApiResponse<BoardPostResponseDto> postBoard(@PathVariable Long workspaceId,
                                                       @RequestBody BoardPostRequestDto requestDto,
                                                       @AuthenticationPrincipal AuthUser authUser) {
        BoardPostResponseDto responseDto = boardService.postBoard(workspaceId,requestDto,authUser);

        return ApiResponse.onSuccess(responseDto);
    }



}
