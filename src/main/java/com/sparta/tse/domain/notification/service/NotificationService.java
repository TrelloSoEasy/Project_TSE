
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
    public ApiResponse<String> notifyMemberAdded(NotificationRequestDto notificationRequestDto) {
        String message = String.format("%s님이 카드 %d번에 추가되었습니다.", notificationRequestDto.getNickname(),
                notificationRequestDto.getCardId()
        );
        return discordSender.sendNotification(message);
    }
   /* // 멤버 추가 알림
    public ApiResponse<String> notifyMemberAdded(NotificationRequestDto notificationRequestDto) {
        String message = String.format("s님이 카드에 추가되었습니다. 카드 ID : %d", notificationRequestDto.getNickname());
        return discordSender.sendNotification(message);
    }*/

    // 멤버 삭제 알림
   /* public ApiResponse<String> notifyMemberDeleted(NotificationRequestDto notificationRequestDto) {
        String message = String.format("%s 멤버가 삭제되었습니다.", notificationRequestDto.getNickname());
        return discordSender.sendNotification(message);
    }*/

    // 카드 변경 알림
    public ApiResponse<String> notifyCardUpdated(NotificationRequestDto notificationRequestDto) {
        String message = String.format("%s님이 카드(%d)를 수정하였습니다.", notificationRequestDto.getNickname(),
                notificationRequestDto.getCardId());
        return discordSender.sendNotification(message);
    }

    // 댓글 작성 알림
    public ApiResponse<String> notifyCommentUpdated(NotificationRequestDto notificationRequestDto) {
        String message = String.format("%s님의 댓글이 작성되었습니다.", notificationRequestDto.getNickname());
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
