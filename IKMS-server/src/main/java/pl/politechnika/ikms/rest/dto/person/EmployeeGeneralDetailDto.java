package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;
import pl.politechnika.ikms.rest.dto.MinimalDto;

@Data
public class EmployeeGeneralDetailDto extends AbstractDto {

    private Long id;

    private MinimalDto<Long, String> personalData;

    private MinimalDto<Long, String> user;

    private EmployeeRole employeeRole;

    private String nip;

    private String name;

    private String surname;
}
