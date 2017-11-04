package pl.politechnika.ikms.rest.controller.notification;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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


@RestController
@RequestMapping(value = "api/notification")
@RequiredArgsConstructor
public class NotificationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final @NonNull NotificationService notificationService;

    private final @NonNull NotificationEntityMapper notificationEntityMapper;

    private final @NonNull JwtTokenUtil jwtTokenUtil;

    private final @NonNull UserService userService;


    @PostMapping("/user/{recipient_username}")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto notifyUser(@RequestBody @Valid NotificationDto notificationDto,
                                      @PathVariable("recipient_username") String recipient_username,
                                      HttpServletRequest request){
        NotificationEntity notificationEntity = notificationService
                .create(notificationEntityMapper.convertToEntity(notificationDto), recipient_username, this.getUserByUsernameFromToken(request).getUsername());
        return notificationEntityMapper.convertToDto(notificationEntity);
    }


    @GetMapping("/myNotifications")
    public Page<NotificationDto> getMyNotification(Pageable pageable, HttpServletRequest request){
        UserEntity user = getUserByUsernameFromToken(request);
        Page<NotificationEntity> myNotifications = notificationService.findMyNotificationByPage(user, pageable);

        return myNotifications.map(notificationEntityMapper::convertToDto);
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NotificationDto getNotificationById(@PathVariable("notificationId") Long notificationId){

        return notificationEntityMapper.convertToDto(notificationService.findOne(notificationId));
    }

    @GetMapping("/myNotifications/{notificationId}")
    public NotificationDto getMyOneNotification(@PathVariable("notificationId") Long notificationId,
                                                HttpServletRequest request){
        UserEntity user = getUserByUsernameFromToken(request);
        NotificationEntity notificationEntity = notificationService.findMyNotificationById(notificationId, user);

        return notificationEntityMapper.convertToDto(notificationEntity);
    }

    @DeleteMapping("/myNotifications/{notificationId}")
    public void deleteMyNotification(@PathVariable("notificationId") Long notificationId,
                                     HttpServletRequest request){
        UserEntity user = getUserByUsernameFromToken(request);
        notificationService.deleteMyNotification(notificationId, user);

    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNotificationById(@PathVariable("notificationId") Long notificationId){
        notificationService.deleteById(notificationId);
    }

    //TODO: [Arek] W późniejszym czasie trzeba to wynieść gdzieś niżej bo to będzie w wielu miejscach potrzebne
    public UserEntity getUserByUsernameFromToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return userService.getUserByUsername(username);

    }



}
