package pl.politechnika.ikms.service.schedule;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ScheduleActivityService extends GenericService<ScheduleActivityEntity> {
    List<ScheduleActivityDto> addMany(List<ScheduleActivityDto> activityDtos);

    List<String> validateOne(ScheduleActivityDto dto);

    List<ScheduleActivityDto> getAllFor(String forWho, Long id);

    List<ScheduleActivityDto> getAllForLoggedEmployee(HttpServletRequest request);
}
