package pl.politechnika.ikms.rest.mapper.schedule;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.repository.classroom.ClassroomRepository;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityErrorsRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ScheduleActivityEntityMapper extends AbstractModelMapper<ScheduleActivityEntity,ScheduleActivityDto> {

    private final @NonNull ClassroomRepository classroomRepository;
    private final @NonNull ChildRepository childRepository;
    private final @NonNull EmployeeRepository employeeRepository;
    private final @NonNull GroupRepository groupRepository;
    private final @NonNull ScheduleActivityErrorsRepository activityErrorsRepository;

    public ScheduleActivityEntityMapper(
            ModelMapper modelMapper,
            ClassroomRepository classroomRepository,
            ChildRepository childRepository,
            EmployeeRepository employeeRepository,
            GroupRepository groupRepository,
            ScheduleActivityErrorsRepository activityErrorsRepository) {
        super(modelMapper);
        this.classroomRepository = classroomRepository;
        this.childRepository = childRepository;
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
        this.activityErrorsRepository = activityErrorsRepository;
    }

    @Override
    public ScheduleActivityDto convertToDto(ScheduleActivityEntity scheduleActivityEntity) {
        ScheduleActivityDto dto = modelMapper.map(scheduleActivityEntity, ScheduleActivityDto.class);

        if (Objects.nonNull(scheduleActivityEntity.getClassroom())) {
            dto.setClassroom(new MinimalDto<>(scheduleActivityEntity.getClassroom().getId(), scheduleActivityEntity.getClassroom().getName()));
        }

        if (Objects.nonNull(scheduleActivityEntity.getGroup())) {
            dto.setGroup(new MinimalDto<>(scheduleActivityEntity.getGroup().getId(), scheduleActivityEntity.getGroup().getName()));
        }

        if (Objects.nonNull(scheduleActivityEntity.getChildren())) {
            List<MinimalDto<Long, String>> children = scheduleActivityEntity.getChildren().stream()
                    .map(s -> new MinimalDto<>(s.getId(), s.getPersonalData().getName() + " " + s.getPersonalData().getSurname()))
                    .collect(Collectors.toList());
            dto.setChildren(children);
        }

        if (Objects.nonNull(scheduleActivityEntity.getEmployees())) {
            List<MinimalDto<Long, String>> employees = scheduleActivityEntity.getEmployees().stream()
                    .map(s -> new MinimalDto<>(s.getId(), s.getPersonalData().getName() + " " + s.getPersonalData().getSurname()))
                    .collect(Collectors.toList());
            dto.setEmployees(employees);
        }

        if (Objects.nonNull(scheduleActivityEntity.getErrors())) {
            List<MinimalDto<Long, String>> errors = scheduleActivityEntity.getErrors().stream()
                    .map(s -> new MinimalDto<>(s.getId(), s.getMessage()))
                    .collect(Collectors.toList());
            dto.setErrors(errors);
        }

        return dto;
    }

    @Override
    public ScheduleActivityEntity convertToEntity(ScheduleActivityDto scheduleActivityDto) {
        ScheduleActivityEntity entity = modelMapper.map(scheduleActivityDto, ScheduleActivityEntity.class);

        if (Objects.nonNull(scheduleActivityDto.getClassroom())) {
            entity.setClassroom(classroomRepository.getOne(scheduleActivityDto.getClassroom().getId()));
        }

        if (Objects.nonNull(scheduleActivityDto.getGroup())) {
            entity.setGroup(groupRepository.getOne(scheduleActivityDto.getGroup().getId()));
        }

        if (Objects.nonNull(scheduleActivityDto.getChildren())) {
            List<Long> childrenIds = scheduleActivityDto.getChildren().stream()
                    .map(MinimalDto::getId)
                    .collect(Collectors.toList());
            entity.setChildren(childRepository.findAll(childrenIds));
        }

        if (Objects.nonNull(scheduleActivityDto.getEmployees())) {
            List<Long> employeeIds = scheduleActivityDto.getEmployees().stream()
                    .map(MinimalDto::getId)
                    .collect(Collectors.toList());
            entity.setEmployees(employeeRepository.findAll(employeeIds));
        }

        if (Objects.nonNull(scheduleActivityDto.getErrors())) {
            List<Long> errorsIds = scheduleActivityDto.getErrors().stream()
                    .map(MinimalDto::getId)
                    .collect(Collectors.toList());
            entity.setErrors(activityErrorsRepository.findAll(errorsIds));
        }

        return entity;
    }
}
