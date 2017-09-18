package pl.politechnika.ikms.util;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.user.RoleRepository;
import pl.politechnika.ikms.security.JwtTokenUtil;
import pl.politechnika.ikms.security.JwtUserFactory;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Data
public class AuthProvider {

    private final @NonNull JwtTokenUtil jwtTokenUtil;
    private final @NonNull RoleRepository roleRepository;

    private UserEntity simpleUser;
    private UserEntity adminUser;
    private UserEntity employeeUser;

    public UserEntity createNewSimpleUser(){
        simpleUser = new UserEntity();
        simpleUser.setUsername("user1");
        simpleUser.setPassword("user1");
        simpleUser.setEmail("user@localhost");
        simpleUser.setId(3L);
        simpleUser.setRole(roleRepository.getByName("ROLE_PARENT"));
        simpleUser.setCreatedDate(new Date());
        simpleUser.setEnabled(true);
        simpleUser.setLastLogged(new Date());

        return simpleUser;
    }

    /**
     * Creates user beyond database
     */
    public UserEntity createNewUserEntity(){
        UserEntity exampleUser = new UserEntity();
        exampleUser.setUsername("username");
        exampleUser.setPassword("password");
        exampleUser.setEmail("email@localhost");
        exampleUser.setRole(roleRepository.getByName("ROLE_PARENT"));
        exampleUser.setCreatedDate(new Date());
        exampleUser.setEnabled(true);
        exampleUser.setLastLogged(new Date());

        return exampleUser;
    }

    public UserEntity createNewAdminUser(){
        adminUser = new UserEntity();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin");
        adminUser.setEmail("admin@localhost");
        adminUser.setId(1L);
        adminUser.setRole(roleRepository.getByName("ROLE_ADMIN"));
        adminUser.setCreatedDate(new Date());
        adminUser.setEnabled(true);
        adminUser.setLastLogged(new Date());

        return adminUser;
    }

    public UserEntity createNewEmployeeUser(){
        employeeUser = new UserEntity();
        employeeUser.setUsername("employee");
        employeeUser.setPassword("employee");
        employeeUser.setEmail("employee@localhost");
        employeeUser.setId(2L);
        employeeUser.setRole(roleRepository.getByName("ROLE_EMPLOYEE"));
        employeeUser.setCreatedDate(new Date());
        employeeUser.setEnabled(true);
        employeeUser.setLastLogged(new Date());

        return employeeUser;
    }

    public String generateTokenForUser(UserEntity user){
        return jwtTokenUtil.generateToken(JwtUserFactory.create(user));
    }

}
