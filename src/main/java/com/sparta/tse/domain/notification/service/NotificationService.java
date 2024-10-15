package com.sparta.tse.domain.notification.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;

    private final String discordWebhookUrl =
            "https://discordapp.com/api/webhooks/1295553870671646791/ETFI6_Nw2-87wzm7iXl1-tG36OBOgXbO5fV1E6EwXXriNMGJ0ky92rajvpRfwCmA4PyC";

    public ApiResponse<Void> sendDiscordNotification(String message){
        return sendDiscordNotification(discordWebhookUrl, message);
    }



}
