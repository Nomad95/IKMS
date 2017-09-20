package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeDto extends AbstractDto{

    private Long id;

    @NotNull
    private EmployeeRole employeeRole;

    @NotNull
    private MinimalDto<Long, String> personalData;

    @Size(max = 13)
    private String nip;
}
