package pl.politechnika.ikms.repository.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.domain.user.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{

    Page<NotificationEntity> findNotificationEntityByRecipientOrderByDateOfSendDesc(UserEntity userEntity, Pageable pageable);

    List<NotificationEntity> findNotificationEntityByRecipientOrderByDateOfSendDesc(UserEntity user);

    @Query(value = "select count(n) from notifications n " +
            "join users u ON u.id = n.recipient_id " +
            "where u.username = :username AND n.was_read = :was_read", nativeQuery = true)
    Optional<Long> countByRecipient_UsernameAndWasRead(@Param("username") String username,
                                                       @Param("was_read") Boolean was_read);
}