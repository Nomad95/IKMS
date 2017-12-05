package pl.politechnika.ikms.rest.controller.notification;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;
import pl.politechnika.ikms.rest.dto.notification.NotificationsGroupedBySender;
import pl.politechnika.ikms.rest.dto.notification.SenderDto;
import pl.politechnika.ikms.rest.mapper.notification.NotificationEntityMapper;
import pl.politechnika.ikms.service.notification.NotificationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final @NonNull
    NotificationService notificationService;

    private final @NonNull
    NotificationEntityMapper notificationEntityMapper;


    @PostMapping("/user/{recipientUsername}")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto notifyUser(@RequestBody @Valid NotificationDto notificationDto,
                                      @PathVariable("recipientUsername") String recipientUsername,
                                      HttpServletRequest request) {
        NotificationEntity notificationEntity = notificationService.
                sendNotification(notificationEntityMapper.convertToEntity(notificationDto), recipientUsername, request);
        return notificationEntityMapper.convertToDto(notificationEntity);
    }

    @GetMapping("/myNotifications")
    public Page<NotificationDto> getMyAllNotification(Pageable pageable, HttpServletRequest request) {
        Page<NotificationEntity> myNotifications = notificationService.findMyNotificationByPage(request, pageable);

        return myNotifications.map(notificationEntityMapper::convertToDto);
    }

    @GetMapping("/myAllNotifications")
    public List<NotificationsGroupedBySender> getMyNotification(HttpServletRequest request) {
        List<NotificationEntity> myNotifications = notificationService.findMyNotificationByUser(request);
        List<NotificationDto> notificationDtos = myNotifications.stream().map(notificationEntityMapper::convertToDto).collect(Collectors.toList());

        return notificationDtos.stream()
                .collect(Collectors.groupingBy(e -> new SenderDto(e.getSenderId(), e.getSenderFullName()), Collectors.toList()))
                .entrySet().stream()
                .map(e -> new NotificationsGroupedBySender(
                        e.getKey().getSenderId(),
                        e.getKey().getSenderFullName(),
                        e.getValue().stream()
                                .map(notificationEntityMapper::convertFromNotificationDtoToNotificationWithoutSenderDto)
                                .collect(Collectors.toList()),
                        (int) e.getValue().stream()
                                .filter(e1 -> e1.getWasRead().equals(Boolean.FALSE))
                                .count()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NotificationDto getNotificationById(@PathVariable("notificationId") Long notificationId) {

        return notificationEntityMapper.convertToDto(notificationService.findOne(notificationId));
    }

    @GetMapping("/myNotifications/{notificationId}")
    public NotificationDto getMyOneNotification(@PathVariable("notificationId") Long notificationId,
                                                HttpServletRequest request) {
        NotificationEntity notificationEntity = notificationService.findMyNotificationById(notificationId, request);

        return notificationEntityMapper.convertToDto(notificationEntity);
    }

    @DeleteMapping("/myNotifications/{notificationId}")
    public void deleteMyNotification(@PathVariable("notificationId") Long notificationId,
                                     HttpServletRequest request) {
        notificationService.deleteMyNotification(notificationId, request);

    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNotificationById(@PathVariable("notificationId") Long notificationId) {
        notificationService.deleteById(notificationId);
    }

    @GetMapping(value = "/myNotifications/quantity/unread")
    public String getNumberOfUnreadNotifications(HttpServletRequest request) {
        Long count = notificationService.countNumberOfMyUnreadNotifications(request);

        return "{\"count\": \" " + count + "\"}";
    }

    @PutMapping("/myNotifications/{notificationId}/read")
    public void readNotification(@PathVariable("notificationId") Long notificationId) {
        notificationService.setNotificationToRead(notificationId);
    }

    @GetMapping("/mobile/newest/{lastNotificationId}")
    public List<NotificationDto> getMyNewestNotificationForMobile(@PathVariable("lastNotificationId") Long lastNotificationId,
                                                                                                      HttpServletRequest request){
        List<NotificationEntity> newestNotificationForMobile = notificationService.findNewestNotificationForMobile(lastNotificationId, request);
        List<NotificationDto> newestNotificationDto = new ArrayList<>();
        newestNotificationForMobile
                .stream()
                .map(notification -> newestNotificationDto.add(notificationEntityMapper.convertToDto(notification)))
                .collect(Collectors.toList());

        return newestNotificationDto;
    }
}
