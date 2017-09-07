package pl.politechnika.ikms.integration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Collections;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.domain.user.enums.Roles;
import pl.politechnika.ikms.repository.user.RoleRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityToDtoMapper;
import pl.politechnika.ikms.util.AuthProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserEntityToDtoMapper userMapper;

    private String token;

    private User defaultUser;

    private List<Long> createdIds;

    @Before
    public void init(){
        defaultUser = authProvider.createNewSimpleUser();
        token = authProvider.generateTokenForUser(defaultUser);
        createdIds = new ArrayList<>();
    }

    /**
     * Uwaga na testowanie delete
     */
    @After
    public void rollback(){
        if(!Collections.isEmpty(createdIds))
            createdIds.forEach(id -> userRepository.delete(id));
    }


    @Test
    public void getCreatedUser(){
        //create user
        User savedUser = userRepository.saveAndFlush(authProvider.createNewUserEntity());
        createdIds.add(savedUser.getId());

        given()
                .contentType(ContentType.JSON)
                .header("Auth-Token",token).
        when()
                .get("/api/user/"+savedUser.getId()).
        then()
                .statusCode(200)
                .body("username", equalTo(savedUser.getUsername()))
                .body("email",equalTo(savedUser.getEmail()))
                .body("id", equalTo(savedUser.getId().intValue()))
                .body("username", equalTo(savedUser.getUsername()))
                .body("enabled", equalTo(savedUser.isEnabled()))
                .body("createdDate", notNullValue())
                .body("lastLogged", notNullValue());
    }

    @Test
    public void getUsersPaginated(){

        given()
                .contentType(ContentType.JSON)
                .header("Auth-Token",token)
                .queryParam("size","20")
                .queryParam("page","0").
        when()
                .get("/api/user").
        then()
                .statusCode(200)
                .body("content", hasItem(anything()))
                .body("numberOfElements",is(lessThan(21)))
                .body("number",is(equalTo(0)));
    }

    @Test
    public void createNewUser(){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("email@email");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setUsername("username");
        userRegistrationDto.setRole(Roles.ROLE_USER);

        Number userId =
        given()
                .contentType(ContentType.JSON)
                .header("Auth-Token",token)
                .body(userRegistrationDto).
        when()
                .post("/api/user").
        then()
                .statusCode(201)
                .body("id",is(greaterThan(1)))
                .body("username",is(equalTo(userRegistrationDto.getUsername())))
                .body("email",is(equalTo(userRegistrationDto.getEmail())))
                .body("createdDate",notNullValue())
                .body("enabled",is(equalTo(true))).
        extract()
                .path("id");

        createdIds.add(userId.longValue());
    }


    @Test
    public void creatingNewUserWithInvaliatedFrontendData(){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("ema");
        userRegistrationDto.setPassword("pas");
        userRegistrationDto.setUsername("use");
        userRegistrationDto.setRole(Roles.ROLE_USER);

        given()
                .contentType(ContentType.JSON)
                .header("Auth-Token",token)
                .body(userRegistrationDto).
        when()
                .post("/api/user").
        then()
                .statusCode(400);
    }

    @Test
    public void creatingNewUserWithExistingUsername(){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setEmail("admin");
        userRegistrationDto.setPassword("admin");
        userRegistrationDto.setUsername("admin@localhost");
        userRegistrationDto.setRole(Roles.ROLE_USER);

        given()
                .contentType(ContentType.JSON)
                .header("Auth-Token",token)
                .body(userRegistrationDto).
        when()
                .post("/api/user").
        then()
                .statusCode(500);
    }

    //TODO: create a method for password change and test it
    @Test
    @Transactional
    public void updateUser() throws Exception {
        //create user
        User savedUser = userRepository.saveAndFlush(authProvider.createNewUserEntity());
        createdIds.add(savedUser.getId());
        //convert to DTO
        UserDto userDto = userMapper.convertToDto(savedUser);

        //change user data
        userDto.setEmail("new@email");
        userDto.setLastLogged(new Date());

        mockMvc.perform(
                put("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper.writeValueAsString(userDto))
                .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(userDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(userDto.getEmail())))
                .andExpect(jsonPath("$.lastLogged",is(notNullValue())))
                .andReturn();

    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        //create ADMIN token
        token = authProvider.generateTokenForUser(authProvider.createNewAdminUser());
        //create user
        User savedUser = userRepository.saveAndFlush(authProvider.createNewUserEntity());
        //createdIds.add(savedUser.getId());

        int sizeBeforeDelete = userRepository.findAll().size();

        mockMvc.perform(
                delete("/api/user/"+savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfterDelete = userRepository.findAll().size();
        Assert.assertThat("user was not deleted",sizeAfterDelete,is(equalTo(sizeBeforeDelete-1)));
    }

    @Test
    @Transactional
    public void deleteUserNotByAdmin() throws Exception {
        //create user
        User savedUser = userRepository.saveAndFlush(authProvider.createNewUserEntity());
        createdIds.add(savedUser.getId());

        mockMvc.perform(
                delete("/api/user/"+savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().is(403));

    }
}
