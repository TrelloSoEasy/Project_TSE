package com.sparta.tse.domain.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.tse.common.entity.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DiscordNotificationSender implements NotificationSender {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String discordWebhookUrl =
            "https://discordapp.com/api/webhooks/1295553870671646791/ETFI6_Nw2-87wzm7iXl1-tG36OBOgXbO5fV1E6EwXXriNMGJ0ky92rajvpRfwCmA4PyC";
//    private static final Logger logger = LoggerFactory.getLogger(DiscordNotificationSender.class);



    public ApiResponse sendNotification(String message) {
        // 메세지를 JSON 형식으로 포맷 : 전송하려는 메세지
        // String payload = "{\"content\" : \"" + message + "\"}";

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("content", message);
        String payload;
        try {
            // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
            payload = objectMapper.writeValueAsString(payloadMap);
        } catch (JsonProcessingException e) {
            return ApiResponse.createError("메시지 변환에 실패하였습니다.", 500);
        }
        // http 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // http 요청본문, 헤더를 포함한  httpEntity 생성
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForObject(discordWebhookUrl, request, String.class);
//            logger.info("알림이 성공적으로 전송되었습니다: {}", message);
            return ApiResponse.createSuccess("알림이 성공적으로 전송되었습니다.", 200, message);
        } catch (RestClientException e) {
//            logger.error("알림 전송 실패 : {}", e.getMessage());
            return ApiResponse.createError("알림 전송에 실패하였습니다.", 500);
        }
    }
}