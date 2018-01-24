package pl.politechnika.ikms.rest.controller.schedule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDiaryDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.service.schedule.ScheduleActivityService;
import pl.politechnika.ikms.validators.schedule.ScheduleValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/schedule")
@RequiredArgsConstructor
public class ScheduleActivityController {

    private final @NonNull ScheduleActivityService scheduleActivityService;
    private final @NonNull ScheduleValidator scheduleValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleActivityDto createActivity(@Valid @RequestBody ScheduleActivityDto activityDto){
        return scheduleActivityService.create(activityDto);
    }

    @GetMapping
    public Page<ScheduleActivityDto> getAllActivities(Pageable pageable){
        return scheduleActivityService.findAllPaginated(pageable);
    }

    @GetMapping(value = "/all", params = { "for", "id" })
    public List<ScheduleActivityDto> getAllActivitiesNotPaged(@RequestParam("for") String forWho, @RequestParam("id") Long id){
        List<ScheduleActivityDto> allFor = scheduleActivityService.getAllFor(forWho, id);

        return scheduleValidator.validateMany(allFor);
    }

    @GetMapping(value = "/diary/employee", params = { "id", "day"})
    public List<ScheduleActivityDto> getDiaryActivitiesByDay(@RequestParam("id") Long id, @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<ScheduleActivityDto> dtos = scheduleActivityService.getAllByDayForEmployee(id, day);
        return scheduleValidator.validateMany(dtos);
    }

    @GetMapping(value = "/diary/admin", params = {"day"})
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public List<ScheduleActivityDto> getDiaryAllActivitiesByDayForAdmin(@RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        List<ScheduleActivityDto> dtos = scheduleActivityService.getAllByDay(day);
        return scheduleValidator.validateMany(dtos);
    }

    @GetMapping(value = "/all/employee")
    public List<ScheduleActivityDto> getAllActivitiesForLoggedEmployee(HttpServletRequest request){
        return scheduleActivityService.getAllForLoggedEmployee(request);
    }

    @GetMapping(value = "/{activityId}")
    public ScheduleActivityDto getOneActivity(@PathVariable Long activityId){
        return scheduleActivityService.findOne(activityId);
    }

    @PutMapping
    public ScheduleActivityDto updateActivity(@Valid @RequestBody ScheduleActivityDto activityDto){
        return scheduleActivityService.update(activityDto);
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

    @GetMapping(value = "/diary/{activityId}")
    public ScheduleActivityDiaryDto  getDiaryActivitiesByDay(@PathVariable("activityId") Long activityId){
        return scheduleActivityService.getActivityDetails(activityId);
    }
}
