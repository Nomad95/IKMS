package pl.politechnika.ikms.service.schedule;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDiaryDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleActivityService extends GenericService<ScheduleActivityDto> {
    List<ScheduleActivityDto> addMany(List<ScheduleActivityDto> activityDtos);

    List<String> validateOne(ScheduleActivityDto dto);

    List<ScheduleActivityDto> validateMany(List<ScheduleActivityDto> dto);

    List<ScheduleActivityDto> getAllFor(String forWho, Long id);

    List<ScheduleActivityDto> getAllByDayForEmployee(Long id, LocalDate day);

    List<ScheduleActivityDto> getAllByDay(LocalDate day);

    List<ScheduleActivityDto> getAllForLoggedEmployee(HttpServletRequest request);

    ScheduleActivityDiaryDto getActivityDetails(Long activityId);
}
