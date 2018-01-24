package pl.politechnika.ikms.service.notification.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;
import pl.politechnika.ikms.rest.mapper.notification.NotificationEntityMapper;
import pl.politechnika.ikms.security.JwtUserFacilities;
import pl.politechnika.ikms.service.notification.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl extends AbstractService<NotificationEntity, NotificationDto, NotificationRepository, NotificationEntityMapper> implements NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtUserFacilities jwtUserFacilities;

    public NotificationServiceImpl(NotificationRepository repository, UserRepository userRepository, NotificationEntityMapper converter) {
        super(repository, converter, NotificationEntity.class);
        this.userRepository = userRepository;
    }

    @Override
    public NotificationDto sendNotification(NotificationEntity notification, //TODO:!!!
                                               String recipientUsername,
                                               HttpServletRequest request) {
        Optional<String> senderFullName =
                Optional.ofNullable(personalDataRepository
                        .findNameAndSurNameSeparatedByComma(jwtUserFacilities.pullTokenAndGetUsername(request))
                        .replace(",", " "));

        Optional<UserEntity> sender = Optional.ofNullable(jwtUserFacilities.findUserByUsernameFromToken(request));
        Optional<UserEntity> recipient = Optional.ofNullable(userRepository.findByUsername(recipientUsername));

        notification.setRecipient(recipient
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono odbiorcy o loginie: " + recipientUsername)));
        notification.setDateOfSend(LocalDateTime.now());
        notification.setWasRead(false);
        notification.setSenderFullName(senderFullName
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono adresata o loginie: " + senderFullName)));
        notification.setSenderId(sender
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono odbiorcy o loginie z tokena "))
                .getId());

        return getConverter().convertToDto(getRepository().save(notification));
    }

    @Override
    public Page<NotificationDto> findMyNotificationByPage(HttpServletRequest request, Pageable pageable) {
        UserEntity me = jwtUserFacilities.findUserByUsernameFromToken(request);
        Optional<Page<NotificationEntity>> myNotifications = Optional.ofNullable(getRepository().
                findNotificationEntityByRecipientOrderByDateOfSendDesc(me,
                        pageable));

        Page<NotificationEntity> notificationEntities = myNotifications
                .orElseThrow(() -> new EntityNotFoundException("Użytkownik o loginie " + me.getUsername() + " nie ma żadnych powiadomień"));

        return notificationEntities.map(getConverter()::convertToDto);
    }

    @Override
    public List<NotificationDto> findMyNotificationByUser(HttpServletRequest request) {
        UserEntity me = jwtUserFacilities.findUserByUsernameFromToken(request);

        Optional<List<NotificationEntity>> myNotifications = Optional.ofNullable(getRepository().
                findNotificationEntityByRecipientOrderByDateOfSendDesc(me));

        List<NotificationEntity> notificationEntities = myNotifications
                .orElseThrow(() -> new EntityNotFoundException("Użytkownik o loginie " + me.getUsername() + " nie ma żadnych powiadomień"));

        return notificationEntities.stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }

    @Override
    public NotificationDto findMyNotificationById(Long notificationId, HttpServletRequest request) {
        UserEntity me = jwtUserFacilities.findUserByUsernameFromToken(request);

        NotificationEntity notification = Optional.ofNullable(getRepository().findOne(notificationId))
                .orElseThrow(() -> new EntityNotFoundException("Powiadomienie z id " + notificationId + " nie istnieje"));

        UserEntity notification_recipient = notification.getRecipient();
        if (!notification_recipient.getId().equals(me.getId())) {
            throw new BadCredentialsException("Powiadomienie z id " + notification.getId()
                    + " nie jest użytkownika z id " + me.getId());
        }

        return getConverter().convertToDto(notification);
    }

    @Override
    public void deleteMyNotification(Long notificationId, HttpServletRequest request) {
        UserEntity me = jwtUserFacilities.findUserByUsernameFromToken(request);

        NotificationEntity notification = Optional.ofNullable(getRepository().findOne(notificationId))
                .orElseThrow(() -> new EntityNotFoundException("Powiadomienie z id " + notificationId + " nie istnieje"));

        UserEntity notification_recipient = notification.getRecipient();
        if (!notification_recipient.getId().equals(me.getId())) {
            throw new BadCredentialsException("Powiadomienie z id "
                    + notification.getId() + " nie jest użytkownika z id " + me.getId());
        } else {
            getRepository().delete(notification);
        }
    }

    @Override
    public Long countNumberOfMyUnreadNotifications(HttpServletRequest request) {
        UserEntity me = jwtUserFacilities.findUserByUsernameFromToken(request);

        return getRepository().countByRecipient_UsernameAndWasRead(me.getUsername(), false);
    }

    @Override
    public void setNotificationToRead(Long idNotification) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(
                "update notifications set was_read = TRUE where id = ?", idNotification);
    }

    @Override
    public List<NotificationDto> findNewestNotificationForMobile(Long lastNotificationId, HttpServletRequest request) {
        String myUsername = jwtUserFacilities.pullTokenAndGetUsername(request);

        List<NotificationEntity> newestNotificationForMobile =
                Optional.ofNullable(getRepository().findNewestNotificiationForMobile(lastNotificationId, myUsername))
                        .orElseThrow(() -> new EntityNotFoundException("Brak nowych wiadomości"));

        return newestNotificationForMobile.stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }
}
