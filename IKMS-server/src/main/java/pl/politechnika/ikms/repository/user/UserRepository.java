package pl.politechnika.ikms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.role.RoleDto;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.role.RoleDto(u.role.name) from UserEntity u WHERE u.username = :username")
    RoleDto getRoleByUsername(@Param("username") String username); //TODO: Evict on login?

}
