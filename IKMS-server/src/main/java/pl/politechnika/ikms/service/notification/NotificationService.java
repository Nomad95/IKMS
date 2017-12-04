package pl.politechnika.ikms.service.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.notification.NotificationEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NotificationService extends GenericService<NotificationEntity> {

    NotificationEntity sendNotification(NotificationEntity notificationEntity, String recipient_username, HttpServletRequest request);

    Page<NotificationEntity> findMyNotificationByPage(HttpServletRequest request, Pageable pageable);

    List<NotificationEntity> findMyNotificationByUser(HttpServletRequest request);

    NotificationEntity findMyNotificationById(Long notificationId, HttpServletRequest request);

    void deleteMyNotification(Long notificationId, HttpServletRequest request);

    Long countNumberOfMyUnreadNotifications(HttpServletRequest request);

    void setNotificationToRead(Long idNotification);

    List<NotificationEntity> findNewestNotificationForMobile(Long lastNotificationId, HttpServletRequest request);
}

