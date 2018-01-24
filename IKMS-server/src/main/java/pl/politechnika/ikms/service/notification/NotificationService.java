package pl.politechnika.ikms.service.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NotificationService extends GenericService<NotificationDto> {

    NotificationDto sendNotification(NotificationEntity notificationEntity, String recipient_username, HttpServletRequest request);

    Page<NotificationDto> findMyNotificationByPage(HttpServletRequest request, Pageable pageable);

    List<NotificationDto> findMyNotificationByUser(HttpServletRequest request);

    NotificationDto findMyNotificationById(Long notificationId, HttpServletRequest request);

    void deleteMyNotification(Long notificationId, HttpServletRequest request);

    Long countNumberOfMyUnreadNotifications(HttpServletRequest request);

    void setNotificationToRead(Long idNotification);

    List<NotificationDto> findNewestNotificationForMobile(Long lastNotificationId, HttpServletRequest request);
}

