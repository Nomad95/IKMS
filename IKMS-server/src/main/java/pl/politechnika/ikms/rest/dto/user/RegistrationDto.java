package pl.politechnika.ikms.rest.dto.user;

import lombok.Data;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;

import java.util.List;

@Data
public class RegistrationDto {

    private UserRegistrationDto userRegistrationDto;

    private PersonalDataDto personalData;

    private List<AddressDto> addressess;

    private EmployeeDto employee;

    private ParentDto parent;
}
