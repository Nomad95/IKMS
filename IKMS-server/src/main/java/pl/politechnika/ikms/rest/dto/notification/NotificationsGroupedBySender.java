package pl.politechnika.ikms.rest.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class NotificationsGroupedBySender {

        private Long senderId;

        private String senderFullName;

        private List<NotificationWithoutSenderDto> notifications;

        private int numberOfUnread;

}
