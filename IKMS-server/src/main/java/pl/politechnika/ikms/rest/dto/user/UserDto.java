package pl.politechnika.ikms.rest.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.user.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto {

    private Long id;

    @NotNull
    @Size(max = 30, min = 3)
    private String username;

    @NotNull
    @Size(max = 75, min = 5)
    private String email;

    private boolean enabled;

    private Date lastLogged;

    private Date createdDate;

    private Role role;
}
