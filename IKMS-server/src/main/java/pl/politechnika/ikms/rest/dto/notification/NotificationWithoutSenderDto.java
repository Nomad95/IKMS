package pl.politechnika.ikms.rest.dto.notification;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.notification.enums.Priority;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationWithoutSenderDto extends AbstractDto {

    private Long id;

    @NotNull
    private String content;

    private LocalDateTime dateOfSend;

    private Boolean wasRead;

    private Priority priority;

}
