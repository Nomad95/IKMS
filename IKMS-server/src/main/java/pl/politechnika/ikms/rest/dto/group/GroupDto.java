package pl.politechnika.ikms.rest.dto.group;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupDto extends AbstractDto {

    private Long id;

    @NotNull
    @Size(max = 35)
    private String name;

    @NotNull
    private LocalDate createdDate;

    private boolean active;

    private MinimalDto<Long, String> employee;

    private List<MinimalDto<Long, String>> children;
}
