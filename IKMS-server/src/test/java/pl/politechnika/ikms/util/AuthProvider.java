package pl.politechnika.ikms.util;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.User;
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

    private User simpleUser;
    private User adminUser;
    private User employeeUser;

    public User createNewSimpleUser(){
        simpleUser = new User();
        simpleUser.setUsername("user1");
        simpleUser.setPassword("user1");
        simpleUser.setEmail("user@localhost");
        simpleUser.setId(3L);
        simpleUser.setRole(roleRepository.getByName("ROLE_USER"));
        simpleUser.setCreatedDate(new Date());
        simpleUser.setEnabled(true);
        simpleUser.setLastLogged(new Date());

        return simpleUser;
    }

    /**
     * Creates user beyond database
     */
    public User createNewUserEntity(){
        User exampleUser = new User();
        exampleUser.setUsername("username");
        exampleUser.setPassword("password");
        exampleUser.setEmail("email@localhost");
        exampleUser.setRole(roleRepository.getByName("ROLE_USER"));
        exampleUser.setCreatedDate(new Date());
        exampleUser.setEnabled(true);
        exampleUser.setLastLogged(new Date());

        return exampleUser;
    }

    public User createNewAdminUser(){
        adminUser = new User();
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

    public User createNewEmployeeUser(){
        employeeUser = new User();
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

    public String generateTokenForUser(User user){
        return jwtTokenUtil.generateToken(JwtUserFactory.create(user));
    }

}
