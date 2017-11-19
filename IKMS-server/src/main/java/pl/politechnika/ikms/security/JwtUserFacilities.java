package pl.politechnika.ikms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.service.user.UserService;
import pl.politechnika.ikms.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUserFacilities {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired(required = false)
    private UserService userService = new UserServiceImpl(userRepository);

    public UserEntity findUserByUsernameFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return userService.getUserByUsername(username);
    }

    public void compareMyUsernameWithAnother(HttpServletRequest request, String anotherUsername){
        String token = request.getHeader(tokenHeader);
        String myUsername = jwtTokenUtil.getUsernameFromToken(token);

        if(!myUsername.equals(anotherUsername))
            throw new AccessDeniedException("Masz za małe uprawnienia do wykonywania danego żądania");
    }

}
