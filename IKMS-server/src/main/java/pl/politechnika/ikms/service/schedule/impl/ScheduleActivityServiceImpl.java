package pl.politechnika.ikms.service.schedule.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;

@Service
public class ScheduleActivityServiceImpl extends AbstractService<ScheduleActivityEntity, ScheduleActivityRepository> implements ScheduleActivityService {

    public ScheduleActivityServiceImpl(ScheduleActivityRepository repository) {
        super(repository, ScheduleActivityEntity.class);
    }
}
