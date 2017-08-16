package pl.politechnika.ikms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.Role;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}
