package pl.politechnika.ikms.repository.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.message.MessageEntity;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.domain.user.UserEntity;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    Page<MessageEntity> findMessageEntityByRecipientOrderByDateOfSendDesc(UserEntity userEntity, Pageable pageable);

    Page<MessageEntity> findMessageEntityBySenderOrderByDateOfSendDesc(UserEntity userEntity, Pageable pageable);

    @Query(value = "select * from messages m " +
            "join users u ON u.id = m.recipient_id " +
            "where m.id > :last_message_id AND u.username = :username ", nativeQuery = true)
    List<MessageEntity> findNewestRecievedMassagesForMobile(@Param("last_message_id") Long lastMessageId,
                                                            @Param("username") String username);

    @Query(value = "select * from messages m " +
            "join users u ON u.id = m.sender_id " +
            "where m.id > :last_message_id AND u.username = :username ", nativeQuery = true)
    List<MessageEntity> findNewestSentMassagesForMobile(@Param("last_message_id") Long lastMessageId,
                                                            @Param("username") String username);
}
