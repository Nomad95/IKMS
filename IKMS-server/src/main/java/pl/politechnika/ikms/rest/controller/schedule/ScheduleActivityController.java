package pl.politechnika.ikms.rest.controller.schedule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityEntityMapper;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;
import pl.politechnika.ikms.validators.schedule.ScheduleValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/schedule")
@RequiredArgsConstructor
public class ScheduleActivityController {

    private final @NonNull ScheduleActivityService scheduleActivityService;
    private final @NonNull ScheduleActivityEntityMapper scheduleActivityEntityMapper;
    private final @NonNull ScheduleValidator scheduleValidator;

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

    @GetMapping(value = "/all", params = { "for", "id" })
    public List<ScheduleActivityDto> getAllActivitiesNotPaged(@RequestParam("for") String forWho, @RequestParam("id") Long id){
        List<ScheduleActivityDto> allFor = scheduleActivityService.getAllFor(forWho, id);

        return scheduleValidator.validateMany(allFor);
    }

    @GetMapping(value = "/all/employee")
    public List<ScheduleActivityDto> getAllActivitiesForLoggedEmployee(HttpServletRequest request){
        return scheduleActivityService.getAllForLoggedEmployee(request);
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
    public ResponseEntity<Boolean> deleteActivity(@PathVariable Long activityId){
        scheduleActivityService.deleteById(activityId);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping(value = "/addMany")
    public List<ScheduleActivityDto> updateActivities(@Valid @RequestBody List<ScheduleActivityDto> activityDtos){
        return scheduleActivityService.addMany(activityDtos);
    }

    @PostMapping(value = "/validate/single")
    public ResponseEntity<List<String>> validateActivity(@Valid @RequestBody ScheduleActivityDto activityDto){
        List<String> result = scheduleActivityService.validateOne(activityDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/validate/many")
    public List<ScheduleActivityDto> validateActivities(@Valid @RequestBody List<ScheduleActivityDto> activityDtos){
        return scheduleActivityService.validateMany(activityDtos);
    }
}
