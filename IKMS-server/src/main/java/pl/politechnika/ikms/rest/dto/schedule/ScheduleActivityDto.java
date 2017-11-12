package pl.politechnika.ikms.rest.dto.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleActivityDto extends AbstractDto{

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Size(max = 255)
    private String comment;

    private LocalDateTime activityStart;

    private LocalDateTime activityEnd;

    private List<MinimalDto<Long, String>> errors;

    private MinimalDto<Long, String> classroom;

    private MinimalDto<Long, String> group;

    private List<MinimalDto<Long, String>> employees;

    private List<MinimalDto<Long, String>> children;
}
