package com.sparta.tse.domain.board.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.List.repository.CardListRepository;
import com.sparta.tse.domain.board.dto.request.BoardPostRequestDto;
import com.sparta.tse.domain.board.dto.response.*;
import com.sparta.tse.domain.board.entity.Board;
import com.sparta.tse.domain.board.repository.BoardRepository;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import com.sparta.tse.domain.file.repository.FileRepository;
import com.sparta.tse.domain.file.service.FileService;
import com.sparta.tse.domain.workspace.entity.Workspace;
import com.sparta.tse.domain.workspace.repository.WorkspaceRepository;
import com.sparta.tse.domain.workspaceMember.entity.MemberRole;
import com.sparta.tse.domain.workspaceMember.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    private final CardListRepository cardListRepository;
    private final CardRepository cardRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    @Transactional
    public BoardPostResponseDto postBoard(Long workspaceId, BoardPostRequestDto requestDto, AuthUser authUser, List<MultipartFile> file) throws IOException {
        //워크스페이스멤버 레포에서 이사람의 현재 워크스페이스에서의 권한을 가져온다.
        //USER의 경우에는 보드를 생성할 수 없게 만든다.
        String Role = workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE));
        if (!(Role.equals(MemberRole.ADMIN.name())||Role.equals(MemberRole.OWNER.name()))) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }
        //보드를 만드려는 워크스페이스를 찾아온다.
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(()->
                new ApiException(ErrorStatus._NOT_FOUND_WORKSPACE));
        //제목이 빈경우 예외처리
        if(requestDto.getTitle()==null||requestDto.getTitle().trim().isEmpty()) {
            throw new ApiException(ErrorStatus._TITLE_IS_NULL);
        }
        //이미지가 null인경우 = 백그라운드 색을 넣은경우
        if(requestDto.getBackgroundColor()!=null) {
            Board board = new Board(requestDto.getTitle(),workspace);
            board.addBackgroundColor(requestDto.getBackgroundColor());
            Board savedboard = boardRepository.save(board);

            BoardPostResponseDto responseDto = new BoardPostResponseDto(savedboard.getTitle());
            responseDto.addBoardBackgroundColor(savedboard.getBackGroundColor());
            return responseDto;

        } else if (requestDto.getBackgroundColor()==null) {
            //백그라운드가 null인경우 = 이미지를 넣은경우
            // 이미지 파일이 들어가는 곳
            Board board = new Board(requestDto.getTitle(),workspace);
            Board savedboard = boardRepository.save(board);

            if (file != null && !file.isEmpty()) {
                fileService.uploadFiles(savedboard.getBoardId(), file, FileEnum.BOARD);
            }
            List<File> image = fileService.getImages(savedboard.getBoardId(), FileEnum.BOARD);

            BoardPostResponseDto responseDto = new BoardPostResponseDto(savedboard.getTitle());
            responseDto.addBoardBackgroundImage(image);
            return responseDto;
        } else {
            throw new ApiException(ErrorStatus._INVALID_POST_BOARD_VALUE);
        }
    }
    //보드 닫기 기능,복구 가능
    @Transactional
    public BoardCloseResponseDto closeBoard(Long workspaceId, Long boardId, AuthUser authUser) {
        //유저의 멤버역할 확인
        String memberRole = memberRoleCheck(workspaceId,authUser);
        //멤버스페이스역할이 유저라면 클로즈하거나 리오픈은 불가능
        if(memberRole.equals(MemberRole.USER.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_BOARD)
        );
        //이미 닫힌 보드를 다시 닫을수는 없음
        if(board.isClosed()) {
            throw new ApiException(ErrorStatus._AlREADY_CLOSE_BOARD);
        }
        //보드 닫기
        board.closeBoard();

        return new BoardCloseResponseDto(boardId);
    }
    @Transactional
    public BoardReopenResponseDto reopenBoard(Long workspaceId, Long boardId, AuthUser authUser) {
        String memberRole = memberRoleCheck(workspaceId,authUser);
        //멤버스페이스역할이 유저라면 클로즈하거나 리오픈은 불가능
        if(memberRole.equals(MemberRole.USER.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }

        Board board = boardRepository.findById(boardId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_BOARD)
        );
        //보드가 안닫힌상태라면?
        if(!board.isClosed()) {
            throw new ApiException(ErrorStatus._BOARD_IS_NOT_CLOSED);
        }

        board.reopenBoard();

        return new BoardReopenResponseDto(board.getBoardId());

    }
    public BoardGetClosedBoardsResponseDto getClosedBoard(Long workspaceId, AuthUser authUser) {
        String memberRole = memberRoleCheck(workspaceId,authUser);
        //멤버스페이스역할이 유저라면 볼수 없음
        if(memberRole.equals(MemberRole.USER.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }
        List<Board> ClosedBoards = boardRepository.findByWorkspaceIdAndIsClosed(workspaceId);

        List<String> boardNames = ClosedBoards.stream().map(Board::getTitle).toList();

        return new BoardGetClosedBoardsResponseDto(boardNames);
    }

    public String memberRoleCheck(Long workspaceId, AuthUser authUser) {
        return workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE));
    }

    public BoardGetResponseDto getBoard(Long boardId, AuthUser authUser) {
        //유저여도 확인할 수 있음
        List<ListDto> listDtos = cardListRepository.findListsByBoardId(boardId);
//        for(ListDto listDto : listDtos) {
//            List<CardDto> cardDtos = cardRepository.cardDtoList(listDto.getListId());
//            listDto.addCard(cardDtos);
//        }

        Board savedboard = boardRepository.findById(boardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_BOARD));
        List<File> image = fileService.getImages(savedboard.getBoardId(), FileEnum.BOARD);

        return new BoardGetResponseDto(listDtos, image);
    }

    public void deleteBoard(Long workspaceId, Long boardId, AuthUser authUser) {
        String Role = memberRoleCheck(workspaceId,authUser);

        if(Role.equals(MemberRole.USER.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }

        boardRepository.deleteById(boardId);
    }
}
