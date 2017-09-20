package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;

@Data
public class ParentDto extends AbstractDto {

    private Long id;

    @NotNull
    private MinimalDto<Long, String> personalData;
}
