
package com.sparta.tse.domain.notification.service;


import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.domain.notification.dto.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {


    private final NotificationSender discordSender;

    // 멤버 추가 알림
    public ApiResponse<String> notifyMemberAdded(NotificationRequestDto requestDto) {
        String message = String.format("%s 멤버가 추가되었습니다.", requestDto.getNickname());
        return discordSender.sendNotification(message);
    }

    // 카드 변경 알림
    public ApiResponse<String> notifyCardUpdated(NotificationRequestDto requestDto) {
        String message = String.format("%s님의 카드가 변경되었습니다.", requestDto.getNickname());
        return discordSender.sendNotification(message);
    }

    // 댓글 작성 알림
    public ApiResponse<String> notifyCommentUpdated(NotificationRequestDto requestDto) {
        String message = String.format("%s님의 댓글이 작성되었습니다.", requestDto.getNickname());
        return discordSender.sendNotification(message);
    }
}


/*

다른 서비스 로직에 추가해줘야할 부분

// 멤버 추가
NotificationRequestDto requestDto = new NotificationRequestDto("MEMBER_ADDED", nickname);
notificationService.notifiyMemberAdded(requestDto);

// 댓글 작성
NotificationRequestDto requestDto = new NotificationRequestDto("CARD_UPDATED", nickname);
notificationService.notifiyMemberAdded(requestDto);

// 카드 변경
NotificationRequestDto requestDto = new NotificationRequestDto("CARD_UPDATED", nickname);
notificationService.notifiyMemberAdded(requestDto);
*//*


---*/
