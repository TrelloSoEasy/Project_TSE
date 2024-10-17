package com.sparta.tse.domain.board.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.board.dto.request.BoardPostRequestDto;
import com.sparta.tse.domain.board.dto.response.BoardPostResponseDto;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.board.repository.BoardRepository;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import com.sparta.tse.domain.workspaceMember.entity.MemberRole;
import com.sparta.tse.domain.workspaceMember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public BoardPostResponseDto postBoard(Long workspaceId, BoardPostRequestDto requestDto, AuthUser authUser) {
        String Role = workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE));
        if (!Role.equals(MemberRole.ADMIN.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        if(requestDto.getImage()==null) {
            Board board = new Board(requestDto.getTitle(),workspace);
            board.addBackgroundColor(requestDto.getBackgroundColor());
            Board savedboard = boardRepository.save(board);

            BoardPostResponseDto responseDto = new BoardPostResponseDto(savedboard.getTitle());
            responseDto.addBoardBackgroundColor(savedboard.getBackGroundColor());
            return responseDto;
        } else if (requestDto.getBackgroundColor()==null) {
            // 이미지 파일이 들어가는 곳
            Board board = new Board(requestDto.getTitle(),workspace);
            board.addImage(requestDto.getImage());
            Board savedboard = boardRepository.save(board);

            BoardPostResponseDto responseDto = new BoardPostResponseDto(savedboard.getTitle());
            responseDto.addBoardBackgroundColor(savedboard.getImage());
            return responseDto;
        } else {
            throw new ApiException(ErrorStatus._INVALID_POST_BOARD_VALUE);
        }
    }
}
