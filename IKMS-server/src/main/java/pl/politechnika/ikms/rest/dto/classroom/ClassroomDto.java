package pl.politechnika.ikms.rest.dto.classroom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassroomDto extends AbstractDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    private boolean available;

    private List<MinimalDto<Long, String>> scheduleActivities;
}
