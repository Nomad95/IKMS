package pl.politechnika.ikms.service.schedule.impl;

import com.google.common.collect.Sets;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDiaryDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityEntityMapper;
import pl.politechnika.ikms.security.JwtUserFacilities;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;
import pl.politechnika.ikms.validators.schedule.ScheduleValidator;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleActivityServiceImpl extends AbstractService<ScheduleActivityEntity, ScheduleActivityDto, ScheduleActivityRepository, ScheduleActivityEntityMapper>
        implements ScheduleActivityService {

    public static final String BACKGROUND = "background";
    private final @NonNull ScheduleValidator scheduleValidator;
    private final @NonNull ScheduleActivityEntityMapper scheduleActivityEntityMapper;
    private final @NonNull JwtUserFacilities jwtUserFacilities;
    private final @NonNull EmployeeRepository employeeRepository;
    private final @NonNull ChildEntityMapper childEntityMapper;


    @Autowired
    public ScheduleActivityServiceImpl(
            ScheduleActivityRepository repository, ScheduleValidator scheduleValidator,
            ScheduleActivityEntityMapper scheduleActivityEntityMapper, JwtUserFacilities jwtUserFacilities,
            EmployeeRepository employeeRepository, ChildEntityMapper childEntityMapper, ScheduleActivityEntityMapper converter) {
        super(repository, converter, ScheduleActivityEntity.class);
        this.scheduleValidator = scheduleValidator;
        this.scheduleActivityEntityMapper = scheduleActivityEntityMapper;
        this.jwtUserFacilities = jwtUserFacilities;
        this.employeeRepository = employeeRepository;
        this.childEntityMapper = childEntityMapper;
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

    @Override
    public List<ScheduleActivityDto> validateMany(List<ScheduleActivityDto> dto) {
        return scheduleValidator.validateMany(dto);
    }

    @Override
    public List<ScheduleActivityDto> getAllFor(String forWho, Long id) {
        List<ScheduleActivityDto> activities = getRepository().findAll().stream()
                .map(scheduleActivityEntityMapper::convertToDto)
                .collect(Collectors.toList());

        switch (forWho) {
            case "employee": {
                activities.forEach(act -> {
                    boolean containsId = act.getEmployees().stream()
                            .map(MinimalDto::getId)
                            .anyMatch(id::equals);
                    if (!containsId) {
                        act.setRendering(BACKGROUND);
                    }
                });
                return activities;
            }
            case "child": {
                activities.forEach(act -> {
                    boolean containsId = act.getChildren().stream()
                            .map(MinimalDto::getId)
                            .anyMatch(id::equals);
                    if (!containsId) {
                        act.setRendering(BACKGROUND);
                    }
                });
                return activities;
            }
            case "group": {
                activities.forEach(act -> {
                    if (Objects.nonNull(act.getGroup())) {
                        boolean containsId = act.getGroup().getId().equals(id);
                        if (!containsId) {
                            act.setRendering(BACKGROUND);
                        }
                    } else act.setRendering(BACKGROUND);
                });
                return activities;
            }
            default:
                return activities;
        }
    }

    @Override
    public List<ScheduleActivityDto> getAllByDayForEmployee(Long id, LocalDate day) {
        LocalDateTime beginning = day.atStartOfDay();
        LocalDateTime end = day.atTime(23, 59);
        List<ScheduleActivityEntity> allForEmployeeAndDay = getRepository()
                .findAllForEmployeeAndDay(id, beginning, end);
        return allForEmployeeAndDay.stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleActivityDto> getAllByDay(LocalDate day) {
        LocalDateTime beginning = day.atStartOfDay();
        LocalDateTime end = day.atTime(23, 59);
        List<ScheduleActivityEntity> allByDay = getRepository().findAllByDay(beginning, end);
        return allByDay.stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleActivityDto> getAllForLoggedEmployee(HttpServletRequest request) {
        String username = jwtUserFacilities.pullTokenAndGetUsername(request);
        UserEntity userEntity = Optional.of(jwtUserFacilities.findUserByUsernameFromToken(request))
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika " + username));
        MinimalDto<Long, String> employee = employeeRepository.getEmployeeMinimalByUserId(userEntity.getId());

        return getAllFor("employee", employee.getId());
    }

    @Override
    public ScheduleActivityDiaryDto getActivityDetails(Long activityId) {
        ScheduleActivityEntity activityEntity = getRepository().getOne(activityId);
        HashSet<ChildEntity> children = Sets.newHashSet();
        List<ChildEntity> activityChildren = activityEntity.getChildren();
        List<ChildEntity> groupChildren = activityEntity.getGroup().getChildren();

        children.addAll(activityChildren);
        children.addAll(groupChildren);

        ScheduleActivityDiaryDto scheduleActivityDiaryDto = new ScheduleActivityDiaryDto();
        scheduleActivityDiaryDto.setScheduleActivityDto(scheduleActivityEntityMapper.convertToDto(activityEntity));
        List<ChildDto> childrenDto = children.stream().map(childEntityMapper::convertToDto).collect(Collectors.toList());
        scheduleActivityDiaryDto.setChildren(childrenDto);

        return scheduleActivityDiaryDto;
    }
}
