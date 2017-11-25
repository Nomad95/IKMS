package pl.politechnika.ikms.service.schedule.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityEntityMapper;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;
import pl.politechnika.ikms.validators.schedule.ScheduleValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleActivityServiceImpl extends AbstractService<ScheduleActivityEntity, ScheduleActivityRepository>
        implements ScheduleActivityService {

    private final @NonNull ScheduleValidator scheduleValidator;
    private final @NonNull ScheduleActivityEntityMapper scheduleActivityEntityMapper;


    @Autowired
    public ScheduleActivityServiceImpl(
            ScheduleActivityRepository repository,
            ScheduleValidator scheduleValidator,
            ScheduleActivityEntityMapper scheduleActivityEntityMapper) {
        super(repository, ScheduleActivityEntity.class);
        this.scheduleValidator = scheduleValidator;
        this.scheduleActivityEntityMapper = scheduleActivityEntityMapper;
    }

    @Override
    public List<ScheduleActivityDto> addMany(List<ScheduleActivityDto> activityDtos){

        List<ScheduleActivityEntity> activityEntities = activityDtos.stream()
                .map(scheduleActivityEntityMapper::convertToEntity)
                .collect(Collectors.toList());

        activityEntities =  getRepository().save(activityEntities);
        activityDtos = activityEntities.stream()
                .map(scheduleActivityEntityMapper::convertToDto)
                .collect(Collectors.toList());

        activityDtos = scheduleValidator.validateMany(activityDtos);

        return activityDtos;
    }

    @Override
    public List<String> validateOne(ScheduleActivityDto dto) {
        return scheduleValidator.validateOne(dto);
    }
}
