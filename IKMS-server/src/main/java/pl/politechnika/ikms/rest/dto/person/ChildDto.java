package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.person.enums.DisabilityLevel;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChildDto extends AbstractDto {

    private Long id;

    @NotNull
    private MinimalDto<Long, String> personalData;

    @NotNull
    private MinimalDto<Long, String> parent;

    @Size(max = 300)
    private String diseases;

    @Size(max = 300)
    private String allergies;

    @NotNull
    private DisabilityLevel disabilityLevel;
}
