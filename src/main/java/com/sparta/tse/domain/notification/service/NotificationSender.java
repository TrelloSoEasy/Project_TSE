package com.sparta.tse.domain.notification.service;

import com.sparta.tse.common.entity.ApiResponse;

public interface NotificationSender {
    ApiResponse<String> sendNotification(String message);
}
