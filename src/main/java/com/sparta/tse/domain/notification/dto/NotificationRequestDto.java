package com.sparta.tse.domain.notification.dto;

import com.sparta.tse.domain.notification.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationRequestDto {

    private EventType eventType;
    private String nickname;
}
