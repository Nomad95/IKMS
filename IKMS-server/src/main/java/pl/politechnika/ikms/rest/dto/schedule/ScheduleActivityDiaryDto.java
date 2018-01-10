package pl.politechnika.ikms.rest.dto.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleActivityDiaryDto extends AbstractDto {

    private ScheduleActivityDto scheduleActivityDto;

    List<ChildDto> children;
}
