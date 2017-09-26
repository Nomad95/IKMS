package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.person.enums.Gender;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PersonalDataDto extends AbstractDto {

    @Id
    private Long id;

    @NotNull
    private MinimalDto<Long, String> user;

    @NotEmpty
    @Size(max = 25)
    private String name;

    @Size(max = 25)
    private String secondaryName;

    @NotEmpty
    @Size(max = 35)
    private String surname;

    @NotNull
    @Size(min = 11, max = 11)
    private String pesel;

    @NotNull
    private LocalDate dateOfBirth;

    @NotEmpty
    @Size(max = 30)
    private String placeOfBirth;

    @Size(max = 35)
    private String familyName;

    @Size(max = 25)
    private String mothersMaidenName;

    @Size(max = 25)
    private String fathersName;

    @Size(max = 25)
    private String mothersName;

    @NotNull
    private Gender gender;

    @NotEmpty
    @Size(max = 45)
    private String nationality;

    @Size(max = 12)
    private String contactNumber;

    @Size(max = 12)
    private String faxNumber;
}
