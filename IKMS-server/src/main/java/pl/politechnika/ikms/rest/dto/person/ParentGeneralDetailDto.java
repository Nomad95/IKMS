package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.rest.dto.MinimalDto;

@Data
public class ParentGeneralDetailDto {
    private Long id;

    private MinimalDto<Long, String> personalData;

    private MinimalDto<Long, String> user;

    private String name;

    private String surname;
}
