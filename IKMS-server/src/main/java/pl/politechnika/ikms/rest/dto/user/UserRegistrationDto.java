package pl.politechnika.ikms.rest.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.user.enums.Roles;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRegistrationDto extends AbstractDto{

    @NotNull
    @Size(max = 30, min = 5)
    private String username;

    @Size(max = 30, min = 5)
    private String password;

    @NotNull
    @Size(max = 70, min = 5)
    private String email;

    @NotNull
    private Roles role;

    @Override
    public Long getId() {
        return null;
    }
}
