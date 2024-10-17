package com.sparta.tse.domain.notification.service;


import com.sparta.tse.domain.notification.dto.CardUpdatedNotificationRequestDto;
import com.sparta.tse.domain.notification.dto.CommentAddedNotificationRequestDto;
import com.sparta.tse.domain.notification.dto.MemberAddedNotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {


    private final NotificationSender discordSender;

    // 워크스페이스 멤버 추가 알림
    public void notifyMemberAdded(MemberAddedNotificationRequestDto memberAddedNotificationRequestDto) {
        String message = String.format("%s님이 워크스페이스에 입장하셨습니다.. WorkSpace ID : %d", memberAddedNotificationRequestDto.getNickname(),
                memberAddedNotificationRequestDto.getWorkSpaceId()
        );
        discordSender.sendNotification(message);
    }

    // 카드 변경의 멤버 추가 알림
    public void notifyMemberAddedInCard(CardUpdatedNotificationRequestDto cardUpdatedNotificationRequestDto) {
        String message = String.format("%s님이 카드 %d번에 추가되었습니다.", cardUpdatedNotificationRequestDto.getNickname(),
                cardUpdatedNotificationRequestDto.getCardId()
        );
        discordSender.sendNotification(message);
    }

    // 카드 변경 알림
    public void notifyCardUpdated(CardUpdatedNotificationRequestDto cardUpdatedNotificationRequestDto) {
        String message = String.format("%s님이 카드(%d)를 수정하였습니다.", cardUpdatedNotificationRequestDto.getNickname(),
                cardUpdatedNotificationRequestDto.getCardId()
        );
        discordSender.sendNotification(message);
    }

    // 댓글 작성 알림
    public void notifyCommentUpdated(CommentAddedNotificationRequestDto commentAddedNotificationRequestDto) {
        String message = String.format("%s님의 댓글이 추가되었습니다.", commentAddedNotificationRequestDto.getNickname());
        discordSender.sendNotification(message);
    }
}


/*

다른 서비스 로직에 추가해줘야할 부분

// 멤버 추가
NotificationRequestDto requestDto = new NotificationRequestDto("MEMBER_ADDED", nickname);
notificationService.notifiyMemberAdded(requestDto);

// 카드 변경
NotificationRequestDto requestDto = new NotificationRequestDto("CARD_UPDATED", nickname);
notificationService.notifiyMemberAdded(requestDto);
*//*


---*/
