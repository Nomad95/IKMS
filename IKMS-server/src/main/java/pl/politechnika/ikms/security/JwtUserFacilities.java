package pl.politechnika.ikms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.service.user.UserService;
import pl.politechnika.ikms.service.user.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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

        UserEntity userEntity = Optional.ofNullable(userService.getUserByUsername(pullTokenAndGetUsername(request)))
                .orElseThrow(()-> new EntityNotFoundException("Nie znaleziono użytkownika z username "+
                        pullTokenAndGetUsername(request)));
        return userEntity;
    }

    public void compareMyUsernameWithAnother(HttpServletRequest request, String anotherUsername){

        if(!pullTokenAndGetUsername(request).equals(anotherUsername))
            throw new AccessDeniedException("Masz za małe uprawnienia do wykonywania danego żądania");
    }

    public String pullTokenAndGetUsername(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        return username;
    }

}
