package com.sparta.tse.domain.notification.controller;


import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/trello/notify/discord")
    public ApiResponse<String> notifyDiscord(@RequestBody String message) {

        ApiResponse<String> response = notificationService.notifyDiscord(message);
        return response;
    }

}
