package pl.politechnika.ikms.rest.controller.notification;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;
import pl.politechnika.ikms.rest.mapper.notification.NotificationEntityMapper;
import pl.politechnika.ikms.security.JwtTokenUtil;
import pl.politechnika.ikms.service.notification.NotificationService;
import pl.politechnika.ikms.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final @NonNull
    NotificationService notificationService;

    private final @NonNull
    NotificationEntityMapper notificationEntityMapper;

    private final @NonNull
    JwtTokenUtil jwtTokenUtil;

    private final @NonNull
    UserService userService;


    @PostMapping("/user/{recipientUsername}")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto notifyUser(@RequestBody @Valid NotificationDto notificationDto,
                                      @PathVariable("recipientUsername") String recipientUsername,
                                      HttpServletRequest request) {
        NotificationEntity notificationEntity = notificationService
                .create(notificationEntityMapper.convertToEntity(notificationDto), recipientUsername, this.getUserByUsernameFromToken(request).getUsername());
        return notificationEntityMapper.convertToDto(notificationEntity);
    }


    @GetMapping("/myNotifications")
    public Page<NotificationDto> getMyAllNotification(Pageable pageable, HttpServletRequest request) {
        UserEntity user = getUserByUsernameFromToken(request);
        Page<NotificationEntity> myNotifications = notificationService.findMyNotificationByPage(user, pageable);

        return myNotifications.map(notificationEntityMapper::convertToDto);
    }

    @GetMapping("/myAllNotifications")
    public List<NotificationDto> getMyNotification(HttpServletRequest request) {
        UserEntity user = getUserByUsernameFromToken(request);
        List<NotificationEntity> myNotifications = notificationService.findMyNotificationByUser(user);

        return myNotifications.stream().map(notificationEntityMapper::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NotificationDto getNotificationById(@PathVariable("notificationId") Long notificationId) {

        return notificationEntityMapper.convertToDto(notificationService.findOne(notificationId));
    }

    @GetMapping("/myNotifications/{notificationId}")
    public NotificationDto getMyOneNotification(@PathVariable("notificationId") Long notificationId,
                                                HttpServletRequest request) {
        UserEntity user = getUserByUsernameFromToken(request);
        NotificationEntity notificationEntity = notificationService.findMyNotificationById(notificationId, user);

        return notificationEntityMapper.convertToDto(notificationEntity);
    }

    @DeleteMapping("/myNotifications/{notificationId}")
    public void deleteMyNotification(@PathVariable("notificationId") Long notificationId,
                                     HttpServletRequest request) {
        UserEntity user = getUserByUsernameFromToken(request);
        notificationService.deleteMyNotification(notificationId, user);

    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNotificationById(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteById(notificationId);
    }

    @GetMapping(value = "/myNotifications/quantity/unread")
    public String getNumberOfUnreadNotifications(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        Long count = notificationService.countNumberOfUnreadNotifications(username);

        return "{\"count\": \" " + count + "\"}";
    }

    @PutMapping("/myNotifications/{notificationId}/read")
    public void readNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.setNotificationToRead(notificationId);
    }


    //TODO: [Arek] W późniejszym czasie trzeba to wynieść gdzieś niżej bo to będzie w wielu miejscach potrzebne
    public UserEntity getUserByUsernameFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return userService.getUserByUsername(username);

    }


}
