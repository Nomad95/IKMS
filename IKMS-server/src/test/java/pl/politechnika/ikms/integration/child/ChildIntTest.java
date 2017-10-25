package pl.politechnika.ikms.integration.child;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
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
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.person.enums.DisabilityLevel;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.integration.person.PersonalDataControllerTest;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.rest.mapper.person.ParentEntityMapper;
import pl.politechnika.ikms.rest.mapper.person.PersonalDataEntityMapper;
import pl.politechnika.ikms.util.AuthProvider;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ChildIntTest {

    public static final DisabilityLevel DEFAULT_DISABILITY_LEVEL = DisabilityLevel.NONE;
    public static final String UPDATED_ALLERGIES = "AAAAAAAA";
    public static final String UPDATED_DISEASES = "AAAAAAAA";
    public static final DisabilityLevel UPDATED_DISABILITY_LEVEL = DisabilityLevel.MILD;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private ParentEntityMapper parentEntityMapper;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ChildEntityMapper childEntityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalDataEntityMapper personalDataEntityMapper;

    @Autowired
    private AuthProvider authProvider;

    private String token;

    private PersonalDataEntity parentPersonalData;

    private PersonalDataEntity childPersonalData;

    private ParentEntity parent;

    @Before
    public void instantiate(){
        instatntiateParent();
    }

    @Test
    @Transactional
    @Rollback
    public void getCreatedChild() throws Exception {
        ChildEntity childEntity = createChildEntity(parent, childPersonalData);
        childEntity = childRepository.saveAndFlush(childEntity);

        mockMvc.perform(
                get("/api/child/" + childEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(childEntity.getId().intValue())));
    }

    @Test
    @Transactional
    @Rollback
    public void createChild() throws Exception {

        ChildDto childDto = createChildDto(parent, childPersonalData);

        mockMvc.perform(
                post("/api/child")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(childDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.parent.id",is(parent.getId().intValue())));

    }

    @Test
    @Transactional
    @Rollback
    public void updateChild() throws Exception {
        ChildEntity childEntity = createChildEntity(parent, childPersonalData);
        childEntity = childRepository.saveAndFlush(childEntity);
        ChildDto childDto = childEntityMapper.convertToDto(childEntity);
        childDto.setAllergies(UPDATED_ALLERGIES);
        childDto.setDiseases(UPDATED_DISEASES);
        childDto.setDisabilityLevel(UPDATED_DISABILITY_LEVEL);

        mockMvc.perform(
                put("/api/child")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(childDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allergies",is(UPDATED_ALLERGIES)))
                .andExpect(jsonPath("$.diseases",is(UPDATED_DISEASES)))
                .andExpect(jsonPath("$.disabilityLevel",is(UPDATED_DISABILITY_LEVEL.toString())));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteChild() throws Exception {//todo: delete personal data???
        ChildEntity childEntity = createChildEntity(parent, childPersonalData);
        childEntity = childRepository.saveAndFlush(childEntity);

        int sizeBefore = childRepository.findAll().size();

        mockMvc.perform(
                delete("/api/child/" + childEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = childRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(sizeBefore - 1));
    }

    //todo: wydzielić to ładnie...
    private void instatntiateParent() {
        UserEntity userEntity = createUserEntity();
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        parentPersonalData = PersonalDataControllerTest.createPersonalDataEntity(userEntity,personalDataRepository);
        parent = createParentEntity(parentPersonalData);
        childPersonalData = PersonalDataControllerTest.createPersonalDataWithoutUser(personalDataRepository);

    }

    public ChildEntity createChildEntity(ParentEntity parentEntity, PersonalDataEntity personalDataEntity){
        ChildEntity childEntity = new ChildEntity();
        childEntity.setPersonalData(personalDataEntity);
        childEntity.setDisabilityLevel(DEFAULT_DISABILITY_LEVEL);
        childEntity.setParent(parentEntity);
        return childRepository.saveAndFlush(childEntity);
    }

    public ChildDto createChildDto(ParentEntity parentEntity, PersonalDataEntity personalDataEntity){
        ChildDto childDto = new ChildDto();
        childDto.setPersonalData(new MinimalDto<>(personalDataEntity.getId(),""));
        childDto.setDisabilityLevel(DEFAULT_DISABILITY_LEVEL);
        childDto.setParent(new MinimalDto<>(parentEntity.getId(),""));
        return childDto;
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
