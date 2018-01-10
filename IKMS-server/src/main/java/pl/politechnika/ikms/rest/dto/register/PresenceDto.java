package pl.politechnika.ikms.rest.dto.register;

import lombok.Data;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.time.LocalDateTime;

@Data
public class PresenceDto {

    private MinimalDto<Long, String> child;

    private boolean wasPresent;

    private MinimalDto<Long, String> checkingPerson;

    private LocalDateTime checkingTime;
}
