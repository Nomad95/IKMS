package pl.politechnika.ikms.rest.controller.schedule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityEntityMapper;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/schedule")
@RequiredArgsConstructor
public class ScheduleActivityController {

    private final @NonNull ScheduleActivityService scheduleActivityService;
    private final @NonNull ScheduleActivityEntityMapper scheduleActivityEntityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleActivityDto createActivity(@Valid @RequestBody ScheduleActivityDto activityDto){
        ScheduleActivityEntity addressEntity = scheduleActivityService.create(scheduleActivityEntityMapper.convertToEntity(activityDto));
        return scheduleActivityEntityMapper.convertToDto(addressEntity);
    }

    @GetMapping
    public Page<ScheduleActivityDto> getAllActivities(Pageable pageable){
        return scheduleActivityService.findAllPaginated(pageable).map(scheduleActivityEntityMapper::convertToDto);
    }

    @GetMapping(value = "/all") //TODO: stub
    public List<ScheduleActivityDto> getAllActivitiesNotPaged(Pageable pageable){
        return scheduleActivityService.findAll().stream()
                .map(scheduleActivityEntityMapper::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @GetMapping(value = "/{activityId}")
    public ScheduleActivityDto getOneActivity(@PathVariable Long activityId){
        return scheduleActivityEntityMapper.convertToDto(scheduleActivityService.findOne(activityId));
    }

    @PutMapping
    public ScheduleActivityDto updateActivity(@Valid @RequestBody ScheduleActivityDto activityDto){
        ScheduleActivityEntity addressEntity = scheduleActivityService.update(scheduleActivityEntityMapper.convertToEntity(activityDto));
        return scheduleActivityEntityMapper.convertToDto(addressEntity);
    }

    @DeleteMapping(value = "/{activityId}")
    public void deleteActivity(@PathVariable Long activityId){
        scheduleActivityService.deleteById(activityId);
    }

    @PostMapping(value = "/addMany")
    public List<ScheduleActivityDto> updateActivities(@Valid @RequestBody List<ScheduleActivityDto> activityDtos){
        ArrayList<ScheduleActivityEntity> activities = activityDtos.stream()
                .map(scheduleActivityEntityMapper::convertToEntity)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<ScheduleActivityDto> dtos = scheduleActivityService.addMany(activities).stream()
                .map(scheduleActivityEntityMapper::convertToDto)
                .collect(Collectors.toCollection(ArrayList::new));
        return dtos;
    }
}