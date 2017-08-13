package pl.politechnika.ikms.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // To jest wywolywane kiedy ktos proboje dostac sie na autoryzowany endpoint bez passow
        // Wysylamy zatem 401 Unauthorized i nie przekierowujemy do 'login page'
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not authorized");
    }
}
