package pl.politechnika.ikms.unit.security;

import org.hamcrest.Matchers;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import pl.politechnika.ikms.domain.user.Role;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.security.JwtTokenUtil;
import pl.politechnika.ikms.security.JwtUser;
import pl.politechnika.ikms.security.JwtUserFactory;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtUtilsTest {

    private static final Date DEFAULT_DATE = new Date(0L);

    @Autowired
    @Spy
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.expiration}")
    private Long expiration;

    private String token;

    private User defaultUser;

    @Before
    public void createToken(){
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUsername("user");
        defaultUser.setPassword("user");
        defaultUser.setLastLogged(DEFAULT_DATE);
        defaultUser.setCreatedDate(DEFAULT_DATE);
        defaultUser.setEnabled(true);
        Role role = new Role();
        role.setName("ROLE_USER");
        defaultUser.setRole(role);
        defaultUser.setEmail("dsd@localost");

        UserDetails details = JwtUserFactory.create(defaultUser);
        token = jwtTokenUtil.generateToken(details);
    }

    @Test
    public void getUsernameFromToken(){
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);

        Assert.assertEquals(usernameFromToken,defaultUser.getUsername());
    }

    @Test
    public void getCreatedDateFromToken(){
        Date createdDateFromToken = jwtTokenUtil.getCreatedDateFromToken(token);

        LocalDate creationDate = new DateTime(createdDateFromToken.getTime()).toLocalDate();
        LocalDate expectedCreationDate = new DateTime(new Date().getTime()).toLocalDate();

        Assert.assertEquals(expectedCreationDate, creationDate);
    }

    @Test
    public void getExpirationDateFromToken(){
        Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(token);
        Date createdDateFromToken = jwtTokenUtil.getCreatedDateFromToken(token);

        LocalDate expirationDate = new DateTime(expirationDateFromToken.getTime()).toLocalDate();
        LocalDate expectedExpirationDate = new DateTime(createdDateFromToken.getTime() + expiration * 1000).toLocalDate();

        Assert.assertEquals(expectedExpirationDate, expirationDate);
    }

    @Test
    public void newlyCreatedTokenIsNotExpired(){
        Boolean tokenExpired = jwtTokenUtil.isTokenExpired(token);

        Assert.assertEquals(false, tokenExpired);
    }

    @Test
    public void oldTokenIsExpired(){
        Mockito.when(jwtTokenUtil.getExpirationDateFromToken(Mockito.anyString())).thenReturn(new Date(0L));
        Boolean tokenExpired = jwtTokenUtil.isTokenExpired(token);

        Assert.assertEquals(true, tokenExpired);
    }

    @Test
    public void unexpiredTokenCanBeRefreshed(){
        Boolean canTokenBeRefreshed = jwtTokenUtil.canTokenBeRefreshed(token);

        Assert.assertEquals(true, canTokenBeRefreshed);
    }

    @Test
    public void expiredTokenCantBeRefreshed(){
        Mockito.when(jwtTokenUtil.getExpirationDateFromToken(Mockito.anyString())).thenReturn(new Date(0L));
        Mockito.when(jwtTokenUtil.isTokenExpired(Mockito.anyString())).thenReturn(Boolean.TRUE);
        Boolean canTokenBeRefreshed = jwtTokenUtil.canTokenBeRefreshed(token);

        Assert.assertEquals(false, canTokenBeRefreshed);
    }

    @Test
    public void tokenIsVerified(){
        JwtUser jwtUser = JwtUserFactory.create(defaultUser);
        Boolean isValidated = jwtTokenUtil.validateToken(token, jwtUser);

        Assert.assertThat(isValidated, Matchers.is(Boolean.TRUE));
    }

    @Test
    public void tokenIsNotVerifiedWithWrongUsername(){
        defaultUser.setUsername("user2");
        JwtUser jwtUser = JwtUserFactory.create(defaultUser);
        Boolean isValidated = jwtTokenUtil.validateToken(token, jwtUser);

        Assert.assertThat(isValidated, Matchers.is(Boolean.FALSE));
    }

    @Test
    public void tokenIsNotVerifiedWhenIsExpired(){
        Mockito.when(jwtTokenUtil.getExpirationDateFromToken(Mockito.anyString())).thenReturn(new Date(0L));
        Mockito.when(jwtTokenUtil.isTokenExpired(Mockito.anyString())).thenReturn(Boolean.TRUE);
        JwtUser jwtUser = JwtUserFactory.create(defaultUser);
        Boolean isValidated = jwtTokenUtil.validateToken(token, jwtUser);

        Assert.assertThat(isValidated, Matchers.is(Boolean.FALSE));
    }
}
