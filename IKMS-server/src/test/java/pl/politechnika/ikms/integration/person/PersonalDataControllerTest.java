package pl.politechnika.ikms.integration.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.After;
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
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.person.enums.AddressType;
import pl.politechnika.ikms.domain.person.enums.Gender;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.AddressRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;
import pl.politechnika.ikms.rest.mapper.person.PersonalDataEntityMapper;
import pl.politechnika.ikms.util.AuthProvider;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PersonalDataControllerTest {

    private static final String DEFAULT_STRING = "AAAAAAAA";
    private static final String UPDATED_STRING = "BBBBBBBB";
    private static final String DEFAULT_NUMBER = "123123123";
    private static final String UPDATED_NUMBER = "876876876";
    private static final String DEFAULT_PESEL = "95030211876";
    private static final String UPDATED_PESEL = "96949311874";
    private static final LocalDate DEFAULT_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final Gender DEFAULT_GENDER = Gender.MAN;
    private static final Gender UPDATED_GENDER = Gender.WOMAN;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonalDataEntityMapper personalDataEntityMapper;

    @Autowired
    private AuthProvider authProvider;

    private String token;

    private List<Long> usersIds = Lists.newArrayList();

    //jakbyście mieli lepszy pomysł to piszcie, troche mi szkoda już nerwów i czasu
    @After
    public void deleteUnwantedEntries(){
        usersIds.forEach(userRepository::delete);
    }


    @Before
    public void init(){
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
    }

    @Test
    @Transactional
    @Rollback
    public void getCreatedPersonalData() throws Exception {
        //create user and personal data
        UserEntity savedUser = createUserEntity();
        PersonalDataEntity personalData = createPersonalDataEntity(savedUser,personalDataRepository);

        mockMvc.perform(
                get("/api/personalData/" + personalData.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user",is(notNullValue())))
                .andExpect(jsonPath("$.contactNumber",equalTo(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.dateOfBirth",is(DEFAULT_LOCAL_DATE.toString())))
                .andExpect(jsonPath("$.familyName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.fathersName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.faxNumber",equalTo(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.gender",equalTo(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.mothersMaidenName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.mothersName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.name",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.nationality",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.pesel",equalTo(DEFAULT_PESEL)))
                .andExpect(jsonPath("$.secondaryName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.placeOfBirth",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.surname",equalTo(DEFAULT_STRING)));
    }

    @Test
    @Transactional
    @Rollback
    public void createPersonalData() throws Exception {
        UserEntity userEntity = createUserEntity();
        PersonalDataDto personalDataDto = createPersonalDataDto(userEntity);

        mockMvc.perform(
                post("/api/personalData")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Auth-token",token)
                .content(jacksonObjectMapper.writeValueAsString(personalDataDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.id",is(userEntity.getId().intValue())))
                .andExpect(jsonPath("$.contactNumber",equalTo(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.dateOfBirth",is(DEFAULT_LOCAL_DATE.toString())))
                .andExpect(jsonPath("$.familyName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.fathersName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.faxNumber",equalTo(DEFAULT_NUMBER)))
                .andExpect(jsonPath("$.gender",equalTo(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.mothersMaidenName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.mothersName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.name",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.nationality",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.pesel",equalTo(DEFAULT_PESEL)))
                .andExpect(jsonPath("$.secondaryName",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.placeOfBirth",equalTo(DEFAULT_STRING)))
                .andExpect(jsonPath("$.surname",equalTo(DEFAULT_STRING)));
    }

    @Test
    @Transactional
    @Rollback
    public void updatePersonalData() throws Exception {
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalData = createPersonalDataEntity(userEntity,personalDataRepository);
        personalData.setContactNumber(UPDATED_NUMBER);
        personalData.setDateOfBirth(UPDATED_LOCAL_DATE);
        personalData.setFamilyName(UPDATED_STRING);
        personalData.setFathersName(UPDATED_STRING);
        personalData.setFaxNumber(UPDATED_NUMBER);
        personalData.setGender(UPDATED_GENDER);
        personalData.setMothersMaidenName(UPDATED_STRING);
        personalData.setMothersName(UPDATED_STRING);
        personalData.setName(UPDATED_STRING);
        personalData.setNationality(UPDATED_STRING);
        personalData.setPesel(UPDATED_PESEL);
        personalData.setSecondaryName(UPDATED_STRING);
        personalData.setPlaceOfBirth(UPDATED_STRING);
        personalData.setSurname(UPDATED_STRING);
        PersonalDataDto personalDataDto = personalDataEntityMapper.convertToDto(personalData);

        mockMvc.perform(
                put("/api/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(personalDataDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id",is(userEntity.getId().intValue())))
                .andExpect(jsonPath("$.contactNumber",equalTo(UPDATED_NUMBER)))
                .andExpect(jsonPath("$.dateOfBirth",is(UPDATED_LOCAL_DATE.toString())))
                .andExpect(jsonPath("$.familyName",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.fathersName",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.faxNumber",equalTo(UPDATED_NUMBER)))
                .andExpect(jsonPath("$.gender",equalTo(UPDATED_GENDER.toString())))
                .andExpect(jsonPath("$.mothersMaidenName",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.mothersName",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.name",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.nationality",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.pesel",equalTo(UPDATED_PESEL)))
                .andExpect(jsonPath("$.secondaryName",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.placeOfBirth",equalTo(UPDATED_STRING)))
                .andExpect(jsonPath("$.surname",equalTo(UPDATED_STRING)));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllPersonalData() throws Exception {
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalData = createPersonalDataEntity(userEntity,personalDataRepository);

        mockMvc.perform(
                get("/api/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .param("size","30")
                        .param("page","0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number",is(0)))
                .andExpect(jsonPath("$.size",is(Matchers.greaterThan(0))))
                .andExpect(jsonPath("$.content",Matchers.hasSize(Matchers.greaterThan(0))));
    }

    @Test
    @Transactional
    @Rollback
    public void deletePersonalData() throws Exception {
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalData = createPersonalDataEntity(userEntity,personalDataRepository);
        int beforeDelete = personalDataRepository.findAll().size();

        mockMvc.perform(
                delete("/api/personalData/" + personalData.getId().intValue())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = personalDataRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(beforeDelete - 1));

    }

    @Test
    @Transactional
    @Rollback
    public void shouldNotPersistPersonalDataWithIncorrectData() throws Exception {
        UserEntity userEntity = createUserEntity();
        PersonalDataDto personalDataDto = createPersonalDataDto(userEntity);
        //set incorrect pesel
        personalDataDto.setPesel("958383776543");

        mockMvc.perform(
                post("/api/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(personalDataDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    public void deletingPersonalDataDeletesItsAddresses() throws Exception {
        UserEntity userEntity = createUserEntity();
        usersIds.add(userEntity.getId());
        PersonalDataEntity personalData = createPersonalDataEntity(userEntity,personalDataRepository);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressType(AddressType.ADDRESS);
        addressEntity.setPersonalData(personalData);
        addressRepository.saveAndFlush(addressEntity);

        int beforeDelete = personalDataRepository.findAll().size();
        int addressesBeforeDelete = addressRepository.findAll().size();

        mockMvc.perform(
                delete("/api/personalData/" + personalData.getId().intValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = personalDataRepository.findAll().size();
        int addressesAfterDelete = addressRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(beforeDelete - 1));
        Assert.assertThat(addressesAfterDelete,is(addressesBeforeDelete - 1));

    }

    private PersonalDataDto createPersonalDataDto(UserEntity userEntity) {
        PersonalDataDto personalDataDto = new PersonalDataDto();
        personalDataDto.setUser(new MinimalDto<>(userEntity.getId(),userEntity.getUsername()));
        personalDataDto.setContactNumber(DEFAULT_NUMBER);
        personalDataDto.setDateOfBirth(DEFAULT_LOCAL_DATE);
        personalDataDto.setFamilyName(DEFAULT_STRING);
        personalDataDto.setFathersName(DEFAULT_STRING);
        personalDataDto.setFaxNumber(DEFAULT_NUMBER);
        personalDataDto.setGender(DEFAULT_GENDER);
        personalDataDto.setMothersMaidenName(DEFAULT_STRING);
        personalDataDto.setMothersName(DEFAULT_STRING);
        personalDataDto.setName(DEFAULT_STRING);
        personalDataDto.setNationality(DEFAULT_STRING);
        personalDataDto.setPesel(DEFAULT_PESEL);
        personalDataDto.setSecondaryName(DEFAULT_STRING);
        personalDataDto.setPlaceOfBirth(DEFAULT_STRING);
        personalDataDto.setSurname(DEFAULT_STRING);
        return personalDataDto;
    }

    //is static because is long and needed in another classes
    public static PersonalDataEntity createPersonalDataEntity(UserEntity savedUser,PersonalDataRepository personalDataRepository) {
        PersonalDataEntity personalDataEntity = new PersonalDataEntity();
        personalDataEntity.setUser(savedUser);
        personalDataEntity.setContactNumber(DEFAULT_NUMBER);
        personalDataEntity.setDateOfBirth(DEFAULT_LOCAL_DATE);
        personalDataEntity.setFamilyName(DEFAULT_STRING);
        personalDataEntity.setFathersName(DEFAULT_STRING);
        personalDataEntity.setFaxNumber(DEFAULT_NUMBER);
        personalDataEntity.setGender(DEFAULT_GENDER);
        personalDataEntity.setMothersMaidenName(DEFAULT_STRING);
        personalDataEntity.setMothersName(DEFAULT_STRING);
        personalDataEntity.setName(DEFAULT_STRING);
        personalDataEntity.setNationality(DEFAULT_STRING);
        personalDataEntity.setPesel(DEFAULT_PESEL);
        personalDataEntity.setSecondaryName(DEFAULT_STRING);
        personalDataEntity.setPlaceOfBirth(DEFAULT_STRING);
        personalDataEntity.setSurname(DEFAULT_STRING);
        return personalDataRepository.saveAndFlush(personalDataEntity);
    }

    private UserEntity createUserEntity() {
        return userRepository.saveAndFlush(authProvider.createNewUserEntity());
    }
}
