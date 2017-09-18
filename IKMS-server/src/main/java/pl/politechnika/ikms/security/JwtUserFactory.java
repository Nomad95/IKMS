package pl.politechnika.ikms.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.politechnika.ikms.domain.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(UserEntity user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(user.getId());
        jwtUser.setUsername(user.getUsername());
        jwtUser.setPassword(user.getPassword());
        jwtUser.setEnabled(user.isEnabled());
        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
        jwtUser.setAuthorities(grantedAuthorities);
        jwtUser.setCreatedDate(user.getCreatedDate());
        jwtUser.setLastLogged(user.getLastLogged());
        jwtUser.setEmail(user.getEmail());
        return jwtUser;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(UserEntity user) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole().getName());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(simpleGrantedAuthority);
        return grantedAuthorities;
    }

}

