package pl.politechnika.ikms.service.schedule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityError;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityErrorRepository;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityErrorDto;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityErrorMapper;
import pl.politechnika.ikms.service.schedule.ScheduleActivityErrorService;

@Service
public class ScheduleActivityErrorServiceImpl extends
        AbstractService<ScheduleActivityError, ScheduleActivityErrorDto, ScheduleActivityErrorRepository, ScheduleActivityErrorMapper>
        implements ScheduleActivityErrorService {

    @Autowired
    public ScheduleActivityErrorServiceImpl(ScheduleActivityErrorRepository repository,
            ScheduleActivityErrorMapper coverter) {
        super(repository, coverter, ScheduleActivityError.class);
    }

}
