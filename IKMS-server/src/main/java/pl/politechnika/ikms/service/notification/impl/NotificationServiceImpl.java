package pl.politechnika.ikms.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.notification.NotificationRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.service.notification.NotificationService;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class NotificationServiceImpl extends AbstractService<NotificationEntity, NotificationRepository> implements NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private DataSource dataSource;

    public NotificationServiceImpl(NotificationRepository repository, UserRepository userRepository) {
        super(repository, NotificationEntity.class);
        this.userRepository = userRepository;
    }


    @Override
    public NotificationEntity create(NotificationEntity notification, String recipient_username, String sender_username) {
        Optional<String> senderFullName = Optional.ofNullable(personalDataRepository.findNameAndSurNameSeparatedByComma(sender_username)
                .replace(",", " "));
        Optional<UserEntity> foundUser = Optional.ofNullable(userRepository.findByUsername(recipient_username));
        notification.setRecipient(foundUser
                .orElseThrow(()-> new EntityNotFoundException("Nie znaleziono odbiorcy o loginie: "+ recipient_username)));
        notification.setDateOfSend(LocalDate.now());
        notification.setWasRead(false);
        notification.setSenderFullName(senderFullName
                .orElseThrow(()-> new EntityNotFoundException("Nie znaleziono adresata o loginie: " + sender_username  )));

        return super.create(notification);
    }

    @Override
    public Page<NotificationEntity> findMyNotificationByPage(UserEntity user, Pageable pageable) {
        Optional<Page<NotificationEntity>> myNotifications = Optional.ofNullable(getRepository().
                findNotificationEntityByRecipientOrderByDateOfSendDesc(user, pageable));

        return myNotifications
                .orElseThrow(()-> new EntityNotFoundException("Użytkownik o loginie "+ user.getUsername() + " nie ma żadnych powiadomień"));
    }

    @Override
    public NotificationEntity findMyNotificationById(Long notificationId, UserEntity user) {
        NotificationEntity notification = Optional.ofNullable(getRepository().findOne(notificationId))
                .orElseThrow(()-> new EntityNotFoundException("Powiadomienie z id "+ notificationId + " nie istnieje"));

        UserEntity notification_recipient = notification.getRecipient();
        if(!notification_recipient.getId().equals(user.getId())) {
            throw new BadCredentialsException("Powiadomienie z id " + notification.getId() + " nie jest użytkownika z id " + user.getId());
        }

        return notification;
    }

    @Override
    public void deleteMyNotification(Long notificationId, UserEntity user) {
        NotificationEntity notification = Optional.ofNullable(getRepository().findOne(notificationId))
                .orElseThrow(()-> new EntityNotFoundException("Powiadomienie z id "+ notificationId + " nie istnieje"));

        UserEntity notification_recipient = notification.getRecipient();
        if(!notification_recipient.getId().equals(user.getId())) {
            throw new BadCredentialsException("Powiadomienie z id " + notification.getId() + " nie jest użytkownika z id " + user.getId());
        } else{
            delete(notification);
        }
    }

    @Override
    public Long countNumberOfUnreadNotifications(String usernameFromToken) {
        return getRepository().countByRecipient_UsernameAndWasRead(usernameFromToken, false).get();
    }

    @Transactional
    @Override
    public void setNotificationToRead(Long idNotification) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(
                "update notifications set was_read = TRUE where id = ?", idNotification);
    }
}
