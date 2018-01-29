package pl.politechnika.ikms.service.user;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.role.RoleDto;
import pl.politechnika.ikms.rest.dto.user.RegistrationDto;
import pl.politechnika.ikms.rest.dto.user.UserDto;

public interface UserService extends GenericService<UserDto>{
    UserDto getUserByUsername(String username);

    RoleDto getRoleByUsername(String username);

    UserDto create(RegistrationDto dto);
}
