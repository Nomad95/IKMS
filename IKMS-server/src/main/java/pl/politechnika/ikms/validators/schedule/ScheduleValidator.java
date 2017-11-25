package pl.politechnika.ikms.validators.schedule;

import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityDto;
import pl.politechnika.ikms.rest.mapper.schedule.ScheduleActivityErrorMapper;
import pl.politechnika.ikms.service.schedule.ScheduleActivityErrorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScheduleValidator {

    private static final String ERROR_COLOR = "#c54456";
    private static final String VALID_COLOR = "#3f51b5";

    public static final String CLASSROOM_OCCUPIED = "Podana sala jest już zajęta";
    public static final String GROUP_BUSY = "Ta grupa ma w tym czasie zajęcia";
    public static final String EMPLOYEE_BUSY = " prowadzi tym czasie zajęcia";
    public static final String CHILD_BUSY = " uczestniczy w tym czasie na innych zajęciach";

    private final @NonNull ScheduleActivityRepository scheduleActivityRepository;
    private final @NonNull ScheduleActivityErrorMapper errorMapper;
    private final @NonNull ScheduleActivityErrorService errorService;

    public List<String> validateOne(ScheduleActivityDto dto){

        ArrayList<String> errors = Lists.newArrayList();

        if (Objects.nonNull(dto.getClassroom())) {
            int classroomsOccupied = scheduleActivityRepository.checkClassroomOccupancy(
                    dto.getActivityEnd(),
                    dto.getActivityStart(),
                    dto.getClassroom().getId(),
                    dto.getId());

            if (classroomsOccupied != 0) {
                errors.add(CLASSROOM_OCCUPIED);
            }
        }

        if (Objects.nonNull(dto.getGroup())) {
            int groupsBusy = scheduleActivityRepository.checkIfGroupIsBusy(
                    dto.getActivityEnd(),
                    dto.getActivityStart(),
                    dto.getGroup().getId(),
                    dto.getId());

            if (groupsBusy != 0) {
                errors.add(GROUP_BUSY);
            }
        }

        List<MinimalDto<Long, String>> employees = dto.getEmployees();
        if (!employees.isEmpty()) {
            employees.forEach( e -> {
                int employeeBusy = scheduleActivityRepository.checkIfEmployeeIsBusy(
                        dto.getActivityEnd(),
                        dto.getActivityEnd(),
                        e.getId(),
                        dto.getId()
                );

                if (employeeBusy !=0){
                    errors.add(e.getValue() + EMPLOYEE_BUSY);
                }
            });
        }

        List<MinimalDto<Long, String>> children = dto.getChildren();
        if (!children.isEmpty()) {
            children.forEach( e -> {
                int childrenBusy = scheduleActivityRepository.checkIfChildIsBusy(
                        dto.getActivityEnd(),
                        dto.getActivityEnd(),
                        e.getId(),
                        dto.getId()
                );

                if (childrenBusy !=0) {
                    errors.add(e.getValue() + CHILD_BUSY);
                }
            });
        }

        return errors;
    }

    public List<ScheduleActivityDto> validateMany(List<ScheduleActivityDto> dtos){
        dtos.forEach( activity -> {
            activity.getErrors().clear();
            List<String> errorMessages = validateOne(activity);
            if (!CollectionUtils.isEmpty(errorMessages)) {
                activity.setColor(ERROR_COLOR);
            } else activity.setColor(VALID_COLOR);
            errorMessages.forEach( str -> activity.getErrors().add(new MinimalDto<>(null,str)));
        });

        return dtos;
    }
}
