package com.sparta.tse.domain.board.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.board.dto.request.BoardPostRequestDto;
import com.sparta.tse.domain.board.dto.response.*;
import com.sparta.tse.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/TSE")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/{workspaceId}/boards")
    public ApiResponse<BoardPostResponseDto> postBoard(@PathVariable Long workspaceId,
                                                       @RequestPart BoardPostRequestDto requestDto,
                                                       @AuthenticationPrincipal AuthUser authUser,
                                                       @RequestPart(value = "file", required = false) List<MultipartFile> file) throws IOException {
        System.out.println("workspaceId = " + workspaceId);
        BoardPostResponseDto responseDto = boardService.postBoard(workspaceId,requestDto,authUser, file);
        return ApiResponse.onSuccess(responseDto);
    }

    @DeleteMapping("/{workspaceId}/boards/{boardId}/close")
    public ApiResponse<BoardCloseResponseDto> closeBoard(@PathVariable Long workspaceId,
                                                         @PathVariable Long boardId,
                                                         @AuthenticationPrincipal AuthUser authUser) {
        BoardCloseResponseDto responseDto = boardService.closeBoard(workspaceId,boardId,authUser);
        return ApiResponse.onSuccess(responseDto);
    }
    @GetMapping("/{workspaceId}/boards/closed-boards")
    public ApiResponse<BoardGetClosedBoardsResponseDto> getClosedBoard(@PathVariable Long workspaceId,
                                                                       @AuthenticationPrincipal AuthUser authUser) {
        BoardGetClosedBoardsResponseDto responseDto = boardService.getClosedBoard(workspaceId,authUser);
        return ApiResponse.onSuccess(responseDto);
    }
    @GetMapping("/boards/{boardId}")
    public ApiResponse<BoardGetResponseDto> getBoard(@PathVariable Long boardId,
                                                     @AuthenticationPrincipal AuthUser authUser) {
        BoardGetResponseDto responseDto = boardService.getBoard(boardId,authUser);
        return ApiResponse.onSuccess(responseDto);
    }
    @PutMapping("/{workspaceId}/boards/{boardId}/reopen")
    public ApiResponse<BoardReopenResponseDto> reopenBoard(@PathVariable Long workspaceId,
                                                           @PathVariable Long boardId,
                                                           @AuthenticationPrincipal AuthUser authUser) {
        BoardReopenResponseDto responseDto = boardService.reopenBoard(workspaceId,boardId,authUser);
        return ApiResponse.onSuccess(responseDto);
    }

    @DeleteMapping("/{workspaceId}/boards/{boardId}")
    public ApiResponse<String> deleteBoard(@PathVariable Long workspaceId,
                                         @PathVariable Long boardId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        boardService.deleteBoard(workspaceId,boardId,authUser);
        return ApiResponse.onSuccess("보드 삭제 완료");
    }
}
