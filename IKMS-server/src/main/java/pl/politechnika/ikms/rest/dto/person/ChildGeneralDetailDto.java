package pl.politechnika.ikms.rest.dto.person;

import lombok.*;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChildGeneralDetailDto extends AbstractDto {

    private Long id;

    private MinimalDto<Long, String> personalData;

    private MinimalDto<Long, String> parent;

    private String name;

    private String surname;

}
