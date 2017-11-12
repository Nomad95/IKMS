package pl.politechnika.ikms.rest.mapper.classroom;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.classroom.ClassroomDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClassroomEntityMapper extends AbstractModelMapper<ClassroomEntity, ClassroomDto> {

    private final @NonNull ScheduleActivityRepository scheduleActivityRepository;

    public ClassroomEntityMapper(ModelMapper modelMapper, ScheduleActivityRepository scheduleActivityRepository) {
        super(modelMapper);
        this.scheduleActivityRepository = scheduleActivityRepository;
    }

    @Override
    public ClassroomDto convertToDto(ClassroomEntity classroomEntity) {
        ClassroomDto dto = modelMapper.map(classroomEntity, ClassroomDto.class);
        if(Objects.nonNull(classroomEntity.getScheduleActivities())){
            List<MinimalDto<Long, String>> activities = classroomEntity.getScheduleActivities().stream()
                    .map(c -> new MinimalDto<>(c.getId(), c.getTitle()))
                    .collect(Collectors.toList());
            dto.setScheduleActivities(activities);
        }

        return dto;
    }

    @Override
    public ClassroomEntity convertToEntity(ClassroomDto classroomDto) {
        ClassroomEntity entity = modelMapper.map(classroomDto, ClassroomEntity.class);
        if(Objects.nonNull(classroomDto.getScheduleActivities())){
            List<Long> activitiesIds = classroomDto.getScheduleActivities().stream()
                    .map(MinimalDto::getId)
                    .collect(Collectors.toList());
            entity.setScheduleActivities(scheduleActivityRepository.findAll(activitiesIds));
        }

        return entity;
    }

}
