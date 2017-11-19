package pl.politechnika.ikms.repository.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.message.MessageEntity;
import pl.politechnika.ikms.domain.user.UserEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    Page<MessageEntity> findMessageEntityByRecipientOrderByDateOfSendDesc(UserEntity userEntity, Pageable pageable);

    Page<MessageEntity> findMessageEntityBySenderOrderByDateOfSendDesc(UserEntity userEntity, Pageable pageable);

}
