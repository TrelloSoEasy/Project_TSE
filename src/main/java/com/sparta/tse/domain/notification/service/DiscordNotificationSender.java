package com.sparta.tse.domain.notification.service;

import com.sparta.tse.common.entity.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class DiscordNotificationSender implements NotificationSender {

    private final RestTemplate restTemplate;
    private final String discordWebhookUrl =
            "https://discordapp.com/api/webhooks/1295553870671646791/ETFI6_Nw2-87wzm7iXl1-tG36OBOgXbO5fV1E6EwXXriNMGJ0ky92rajvpRfwCmA4PyC";


    public ApiResponse sendNotification(String message) {

        // 메세지 포맷 : 전송하려는 메세지
        String payload = "{\"content\" : \"" + message + "\"}";

        // http 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // http 요청본문, 헤더를 포함한  httpEntity 생성
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForObject(discordWebhookUrl, request, String.class);
            return ApiResponse.createSuccess("알림이 성공적으로 전송되었습니다.", 200, message);
        } catch (RestClientException e) {
            return ApiResponse.createError("알림 전송에 실패하였습니다.", 500);
        }
    }
}

