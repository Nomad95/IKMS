package pl.politechnika.ikms.rest.dto.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;

@Data
@AllArgsConstructor
public class RoleDto extends AbstractDto {

    private String role;
}
