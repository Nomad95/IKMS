package pl.politechnika.ikms.rest.dto.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.notification.enums.Priority;
import pl.politechnika.ikms.rest.dto.user.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationDto extends AbstractDto {

    private Long id;

    @NotNull
    private String content;

    private UserDto recipient;

    private LocalDate dateOfSend;

    private Boolean wasRead;

    private Priority priority;

}
