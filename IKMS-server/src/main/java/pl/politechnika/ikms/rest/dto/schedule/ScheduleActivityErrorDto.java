package pl.politechnika.ikms.rest.dto.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleActivityErrorDto extends AbstractDto{

        private Long id;

        @NotNull
        private String message;

        @NotNull
        private MinimalDto<Long, String> activity;
}
