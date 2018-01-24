package pl.politechnika.ikms.rest.dto.register;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.schedule.ClassesType;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Data
public class RegisterDto extends AbstractDto{

    private Long id;

    private ClassesType classesType;

    private LocalDateTime activityStart;

    private Long leaderId;

    private List<PresenceDto> presences = newArrayList();

    private List<RegisterEntryDto> entries = newArrayList();
}
