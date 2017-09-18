package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeDto extends PersonDto{

    @NotNull
    private EmployeeRole employeeRole;
}
