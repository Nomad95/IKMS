package pl.politechnika.ikms.service.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.security.JwtUser;

import java.util.List;

public interface NotificationService extends GenericService<NotificationEntity> {

    NotificationEntity create(NotificationEntity notificationEntity, String recipient_username);

    Page<NotificationEntity> findMyNotificationByPage(UserEntity user, Pageable pageable);

    NotificationEntity findMyNotificationById(Long notificationId, UserEntity user);

    void deleteMyNotification(Long notificationId, UserEntity user);
}

