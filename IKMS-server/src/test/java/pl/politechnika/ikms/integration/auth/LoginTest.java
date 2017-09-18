package pl.politechnika.ikms.integration.auth;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.security.JwtAuthenticationRequest;
import pl.politechnika.ikms.security.JwtTokenUtil;
import pl.politechnika.ikms.security.JwtUserFactory;
import pl.politechnika.ikms.util.AuthProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LoginTest {

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void loginAndGetToken(){
        UserEntity newSimpleUser = authProvider.createNewSimpleUser();
        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest(newSimpleUser.getUsername(), newSimpleUser.getPassword());

        String token =
        given()
                .contentType(ContentType.JSON)
                .body(jwtAuthenticationRequest).
        when()
                .post("/auth/login").
        then()
                .body("token", Matchers.isA(String.class)).statusCode(200).
        extract()
                .path("token");

        Assert.assertThat(
                "Token was generated wrong",
                newSimpleUser.getUsername(),
                is(equalTo(jwtTokenUtil.getUsernameFromToken(token)))
        );

        Assert.assertThat(
                "Token was generated wrong",
                jwtTokenUtil.validateToken(token, JwtUserFactory.create(newSimpleUser)),
                is(true)
        );
    }

    @Test
    public void logWithBadCredentialsReturnsUnauthorized(){
        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest("random","random");

        given()
                .contentType(ContentType.JSON)
                .body(jwtAuthenticationRequest).
        when()
                .post("/auth/login").
        then()
                .statusCode(401);
    }

    @Test
    public void refreshToken(){
        String token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());

        given()
                .header("Auth-Token",token).
                when()
                .get("/auth/refresh").
                then()
                .statusCode(200);
    }
}
