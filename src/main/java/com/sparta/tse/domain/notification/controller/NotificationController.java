package com.sparta.tse.domain.notification.controller;


import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.notification.dto.NotificationRequestDto;
import com.sparta.tse.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 테스트용
    @PostMapping("/member-added")
    public ApiResponse<String> notifyMemberAdded(@RequestBody NotificationRequestDto requestDto) {
        return notificationService.notifyMemberAdded(requestDto);
    }

    // 테스트용
    @PostMapping("/card-updated")
    public ApiResponse<String> notifyCardUpdated(@RequestBody NotificationRequestDto requestDto) {
        return notificationService.notifyCardUpdated(requestDto);
    }

    // 테스트용
    @PostMapping("/comment-added")
    public ApiResponse<String> notifyCommentUpdated(@RequestBody NotificationRequestDto requestDto) {
        return notificationService.notifyCommentUpdated(requestDto);
    }
}
