package com.sparta.tse.domain.board.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.board.dto.request.BoardPostRequestDto;
import com.sparta.tse.domain.board.dto.response.BoardPostResponseDto;
import com.sparta.tse.domain.user.entity.User;
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

    @Transactional
    public BoardPostResponseDto postBoard(Long workspaceId, BoardPostRequestDto requestDto, AuthUser authUser) {
        String Role = workspaceMemberRepository.findRoleByEmail(authUser.getUserId(),workspaceId).orElseThrow(
                ()->new ApiException(ErrorStatus._NOT_FOUND_ROLE));
        if (!Role.equals(MemberRole.ADMIN.name())) {
            throw new ApiException(ErrorStatus._FORBIDDEN_ACCESS);
        }

        return null;




    }
}
