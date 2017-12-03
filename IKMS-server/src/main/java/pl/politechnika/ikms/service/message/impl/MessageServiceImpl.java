package pl.politechnika.ikms.service.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.message.MessageEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.message.MessageRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.security.JwtUserFacilities;
import pl.politechnika.ikms.service.message.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Transactional
public class MessageServiceImpl extends AbstractService<MessageEntity, MessageRepository> implements MessageService {

    @Autowired
    private JwtUserFacilities jwtUserFacilities;

    @Autowired
    private UserRepository userRepository;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    public MessageServiceImpl(MessageRepository repository) {
        super(repository);
    }

    @Override
    public MessageEntity sendMessage(MessageEntity messageEntity, HttpServletRequest request, String recipientUsername) {

        UserEntity sender = Optional.ofNullable(jwtUserFacilities.findUserByUsernameFromToken(request))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania twojego username z tokena "));
        UserEntity recipient = Optional.ofNullable(userRepository.findByUsername(recipientUsername))
                .orElseThrow(()-> new EntityNotFoundException("Nie ma użytkownika o nazwie "+ recipientUsername));
        String recipientFullName =
                Optional.ofNullable(personalDataRepository
                        .findNameAndSurNameSeparatedByComma(recipientUsername)
                        .replace(",", " "))
                        .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono odbiorcy o loginie: " + recipientUsername));
        String senderFullName =
                Optional.ofNullable(personalDataRepository
                        .findNameAndSurNameSeparatedByComma(jwtUserFacilities.pullTokenAndGetUsername(request))
                        .replace(",", " "))
                        .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono odbiorcy o loginie z tokena"));

        messageEntity.setSender(sender);
        messageEntity.setRecipient(recipient);
        messageEntity.setDateOfSend(LocalDateTime.now());
        messageEntity.setWasRead(false);
        messageEntity.setRecipientUsername(recipient.getUsername());
        messageEntity.setSenderUsername(sender.getUsername());
        messageEntity.setRecipientFullName(recipientFullName);
        messageEntity.setSenderFullName(senderFullName);

        getRepository().save(messageEntity);

        return messageEntity;
    }

    @Override
    public Page<MessageEntity> findAllOfMyReceivedMessage(Pageable pageable, HttpServletRequest request) {
        UserEntity recipient = Optional.ofNullable(jwtUserFacilities.findUserByUsernameFromToken(request))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania twojego username z tokena "));

        Page<MessageEntity> myMessagesToReceived = Optional.ofNullable(getRepository()
                .findMessageEntityByRecipientOrderByDateOfSendDesc(recipient, pageable))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania wiadomości użytkownika o nazwie "
                        + recipient.getUsername()));

        return myMessagesToReceived;
    }

    @Override
    public MessageEntity findReceivedMessageById(Long idMessage, HttpServletRequest request) {
        MessageEntity message = Optional.ofNullable(getRepository().findOne(idMessage))
                .orElseThrow(()-> new EntityNotFoundException("Wiadomość o id " + idMessage + " nie istnieje"));

        if(!message.getWasRead()){
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            this.setMessageToRead(jdbcTemplate, idMessage);
            message.setWasRead(true);
        }

        return message;
    }

    @Override
    public Page<MessageEntity> findAllOfMySentMessage(Pageable pageable, HttpServletRequest request) {
        UserEntity sender = Optional.ofNullable(jwtUserFacilities.findUserByUsernameFromToken(request))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania username z tokena"));

        Page<MessageEntity> mySentMessage = Optional.ofNullable(getRepository()
                .findMessageEntityBySenderOrderByDateOfSendDesc(sender, pageable))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania wiadomości użytkownika o nazwie "
                        + sender.getUsername()));

        return mySentMessage;
    }

    @Override
    public MessageEntity findSentMessageById(Long idMessage, HttpServletRequest request) {
        MessageEntity message = Optional.ofNullable(getRepository().findOne(idMessage))
                .orElseThrow(()-> new EntityNotFoundException("Wiadomość o id " + idMessage + " nie istnieje"));

        return message;
    }

    @Override
    public void deleteMessageFromReceived(Long idMessage, HttpServletRequest request) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MessageEntity messageToDelete = Optional.ofNullable(getRepository().findOne(idMessage))
                .orElseThrow(()-> new EntityNotFoundException("Brak wiadomości z id "+ idMessage));

        String usernameRecipient = messageToDelete.getRecipient().getUsername();

        checkNotNull(usernameRecipient, "Nazwa odbiorcy wiadomości o id " + idMessage + " jest nullem");
        jwtUserFacilities.compareMyUsernameWithAnother(request, usernameRecipient);

        if(messageToDelete.getSender() != null)
            updateRecipientColumn(jdbcTemplate,null,idMessage);
        else
            getRepository().delete(messageToDelete);
    }

    @Override
    public void deleteMessageFromSent(Long idMessage, HttpServletRequest request) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MessageEntity messageToDelete = Optional.ofNullable(getRepository().findOne(idMessage))
                .orElseThrow(()-> new EntityNotFoundException("Brak wiadomości z id "+ idMessage));

        String usernameSender = messageToDelete.getSender().getUsername();

        checkNotNull(usernameSender, "Nazwa odbiorcy wiadomości o id " + idMessage + " jest nullem");
        jwtUserFacilities.compareMyUsernameWithAnother(request, usernameSender);

        if(messageToDelete.getRecipient() != null)
            updateSenderColumn(jdbcTemplate,null,idMessage);
        else
            getRepository().delete(messageToDelete);
    }

    @Override
    public int countNumberOfUnreadMessages(HttpServletRequest request) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserEntity userEntity = Optional.ofNullable(jwtUserFacilities.findUserByUsernameFromToken(request))
                .orElseThrow(()-> new EntityNotFoundException("Błąd podczas pobierania twojego username z tokena "));
        Long id = userEntity.getId();
        String sql = "SELECT count(*) FROM messages WHERE recipient_id = ? AND was_read=FALSE ";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count;
    }


    public void updateSenderColumn(JdbcTemplate jdbcTemplate, String senderUsername, Long idUser) {
        jdbcTemplate.update(
                "update messages set sender_id = ? where id = ?",
                senderUsername, idUser);
    }

    public void updateRecipientColumn(JdbcTemplate jdbcTemplate, String recipientUsername, Long idUser) {
        jdbcTemplate.update(
                "update messages set recipient_id = ? where id = ?",
                recipientUsername, idUser);
    }

    public void setMessageToRead(JdbcTemplate jdbcTemplate,  Long idUser) {
        jdbcTemplate.update(
                "update messages set was_read = TRUE where id = ?", idUser);
    }
}
