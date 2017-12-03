package pl.politechnika.ikms.rest.mapper.notification;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.notification.NotificationEntity;
import pl.politechnika.ikms.repository.notification.NotificationRepository;
import pl.politechnika.ikms.rest.dto.notification.NotificationDto;

import static java.util.Objects.nonNull;

@Component
public class NotificationEntityMapper extends AbstractModelMapper<NotificationEntity, NotificationDto> {

    private final @NonNull NotificationRepository notificationRepository;

    public NotificationEntityMapper(ModelMapper modelMapper, NotificationRepository notificationRepository) {
        super(modelMapper);
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDto convertToDto(NotificationEntity notificationEntity) {
         NotificationDto notificationDto = modelMapper.map(notificationEntity, NotificationDto.class);
         if(nonNull(notificationEntity.getRecipient())){
             notificationDto.setSenderId(notificationEntity.getSenderId());
         }

        return notificationDto;
    }

    @Override
    public NotificationEntity convertToEntity(NotificationDto notificationDto) {
        NotificationEntity notificationEntity = modelMapper.map(notificationDto, NotificationEntity.class);
        if(nonNull(notificationDto.getSenderId())){
            notificationEntity.setSenderId(notificationDto.getSenderId());
        }

        return notificationEntity;
    }
}
