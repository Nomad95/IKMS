package pl.politechnika.ikms.integration.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;
import pl.politechnika.ikms.rest.mapper.person.ParentEntityMapper;
import pl.politechnika.ikms.rest.mapper.person.PersonalDataEntityMapper;
import pl.politechnika.ikms.util.AuthProvider;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ParentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentEntityMapper parentEntityMapper;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private PersonalDataEntityMapper personalDataEntityMapper;

    @Autowired
    private AuthProvider authProvider;

    private String token;

    @Test
    @Transactional
    @Rollback
    public void getCreatedParent() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        ParentEntity parentEntity = createParentEntity(personalDataEntity);

        mockMvc.perform(
                get("/api/parent/" + parentEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())));
    }

    @Test
    @Transactional
    @Rollback
    public void createParent() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        ParentDto parentDto = createParentDto(personalDataEntity);

        mockMvc.perform(
                post("/api/parent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(parentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalData.id",is(personalDataEntity.getId().intValue())));

    }

    //@Test //TODO: narazie nie ma nic do edycji
    @Transactional
    @Rollback
    public void updateParent() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        ParentEntity parentEntity = createParentEntity(personalDataEntity);
    }

    @Test
    @Transactional
    @Rollback
    public void getAllParents() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        createParentEntity(personalDataEntity);

        mockMvc.perform(
                get("/api/parent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .param("size","30")
                        .param("page","0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", CoreMatchers.is(0)))
                .andExpect(jsonPath("$.size", CoreMatchers.is(Matchers.greaterThan(0))))
                .andExpect(jsonPath("$.content",Matchers.hasSize(Matchers.greaterThan(0))));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteParent() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        ParentEntity parentEntity = createParentEntity(personalDataEntity);

        //check employee was deleted but personal data remains
        int sizeBefore = parentRepository.findAll().size();
        int personalDataSizeBefore = personalDataRepository.findAll().size();

        mockMvc.perform(
                delete("/api/parent/" + parentEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = parentRepository.findAll().size();
        int personalDataSizeAfter = personalDataRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(sizeBefore - 1));
        Assert.assertThat(personalDataSizeAfter,is(personalDataSizeBefore));
    }

    private ParentDto createParentDto(PersonalDataEntity personalDataEntity) {
        ParentDto parentDto = new ParentDto();
        parentDto.setPersonalData(new MinimalDto<>(personalDataEntity.getId(),personalDataEntity.getName()));
        return parentDto;
    }

    private ParentEntity createParentEntity(PersonalDataEntity personalDataEntity) {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setPersonalData(personalDataEntity);
        return parentRepository.saveAndFlush(parentEntity);
    }

    private UserEntity createUserEntity() {
        return userRepository.saveAndFlush(authProvider.createNewUserEntity());
    }
}
