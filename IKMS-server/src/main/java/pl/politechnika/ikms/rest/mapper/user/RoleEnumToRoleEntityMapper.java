package pl.politechnika.ikms.rest.mapper.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.Role;
import pl.politechnika.ikms.domain.user.enums.Roles;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.RoleRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleEnumToRoleEntityMapper {

    private final @NonNull RoleRepository roleRepository;

    public Role getRoleFromEnum(Roles roleEnum){
        Optional<Role> role = Optional.of(roleRepository.getByName(roleEnum.toString()));
        return role.orElseThrow(EntityNotFoundException::new);
    }
}
