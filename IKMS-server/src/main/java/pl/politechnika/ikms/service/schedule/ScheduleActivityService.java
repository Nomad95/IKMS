package pl.politechnika.ikms.service.schedule;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;

import java.util.List;

public interface ScheduleActivityService extends GenericService<ScheduleActivityEntity> {
    List<ScheduleActivityEntity> addMany(List<ScheduleActivityEntity> activityDtos);
}
