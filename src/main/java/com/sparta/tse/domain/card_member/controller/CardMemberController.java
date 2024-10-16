package com.sparta.tse.domain.card_member.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card_member.dto.CardMemberResponseDto;
import com.sparta.tse.domain.card_member.dto.CardMemberRoleModifyRequestDto;
import com.sparta.tse.domain.card_member.service.CardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse")
public class CardMemberController {

    private final CardMemberService cardMemberService;

    @PutMapping("/card/member")
    public ApiResponse<CardMemberResponseDto> cardMemberRoleModify(@AuthenticationPrincipal AuthUser user,
                                                                   @RequestBody CardMemberRoleModifyRequestDto requestDto) {
        return ApiResponse.createSuccess("변경 성공", HttpStatus.OK.value(), cardMemberService.cardMemberRoleModify(user,requestDto));
    }

}
