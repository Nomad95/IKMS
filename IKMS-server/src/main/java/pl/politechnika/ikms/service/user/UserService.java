package pl.politechnika.ikms.service.user;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.role.RoleDto;

public interface UserService extends GenericService<UserEntity>{
    UserEntity getUserByUsername(String username);

    RoleDto getRoleByUsername(String username);
}
