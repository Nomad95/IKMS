package pl.politechnika.ikms.integration.person;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.person.enums.AddressType;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.AddressRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.rest.mapper.person.AddressEntityMapper;
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
public class AddressControllerTest {

    private static final String DEFAULT_STRING = "AAAAAAAA";
    private static final String UPDATED_STRING = "BBBBBBBB";
    private static final String DEFAULT_POST_CODE = "21-565";
    private static final String UPDATED_POST_CODE = "22-300";
    private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.ADDRESS;
    private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.CORRESPONDENCE_ADDRESS;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressEntityMapper addressEntityMapper;

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
    public void getCreatedAddress() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        AddressEntity addressEntity = createAddressEntity(personalDataEntity);

        mockMvc.perform(
                get("/api/address/" + addressEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.city", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.community", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.country", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.county", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.houseNumber", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.postCode", is(DEFAULT_POST_CODE)))
                .andExpect(jsonPath("$.street", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.voivodeship", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.addressType", is(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.streetNumber", is(11)));

    }

    @Test
    @Transactional
    @Rollback
    public void createAddress() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        AddressDto addressDto = createAddressDto(personalDataEntity);

        mockMvc.perform(
                post("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.city", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.community", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.country", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.county", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.houseNumber", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.postCode", is(DEFAULT_POST_CODE)))
                .andExpect(jsonPath("$.street", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.voivodeship", is(DEFAULT_STRING)))
                .andExpect(jsonPath("$.addressType", is(DEFAULT_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.streetNumber", is(11)));
    }

    @Test
    @Transactional
    @Rollback
    public void updateAddress() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        AddressEntity addressEntity = createAddressEntity(personalDataEntity);

        addressEntity.setCity(UPDATED_STRING);
        addressEntity.setCommunity(UPDATED_STRING);
        addressEntity.setCountry(UPDATED_STRING);
        addressEntity.setCounty(UPDATED_STRING);
        addressEntity.setHouseNumber(UPDATED_STRING);
        addressEntity.setPostCode(UPDATED_POST_CODE);
        addressEntity.setStreet(UPDATED_STRING);
        addressEntity.setVoivodeship(UPDATED_STRING);
        addressEntity.setAddressType(UPDATED_ADDRESS_TYPE);
        addressEntity.setStreetNumber(33);

        AddressDto addressDto = addressEntityMapper.convertToDto(addressEntity);

        mockMvc.perform(
                put("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.city", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.community", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.country", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.county", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.houseNumber", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.postCode", is(UPDATED_POST_CODE)))
                .andExpect(jsonPath("$.street", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.voivodeship", is(UPDATED_STRING)))
                .andExpect(jsonPath("$.addressType", is(UPDATED_ADDRESS_TYPE.toString())))
                .andExpect(jsonPath("$.streetNumber", is(33)));
    }

    @Test
    @Transactional
    @Rollback
    public void deleteAddress() throws Exception {
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        AddressEntity addressEntity = createAddressEntity(personalDataEntity);

        int sizeBefore = addressRepository.findAll().size();

        mockMvc.perform(
                delete("/api/address/"+addressEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = addressRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(sizeBefore - 1));
    }

    private AddressDto createAddressDto(PersonalDataEntity personalDataEntity) {
        AddressDto addressDto = new AddressDto();
        addressDto.setPersonalData(new MinimalDto<>(personalDataEntity.getId(),personalDataEntity.getName()));
        addressDto.setCity(DEFAULT_STRING);
        addressDto.setCommunity(DEFAULT_STRING);
        addressDto.setCountry(DEFAULT_STRING);
        addressDto.setCounty(DEFAULT_STRING);
        addressDto.setHouseNumber(DEFAULT_STRING);
        addressDto.setPostCode(DEFAULT_POST_CODE);
        addressDto.setStreet(DEFAULT_STRING);
        addressDto.setVoivodeship(DEFAULT_STRING);
        addressDto.setAddressType(DEFAULT_ADDRESS_TYPE);
        addressDto.setStreetNumber(11);
        return addressDto;
    }

    private AddressEntity createAddressEntity(PersonalDataEntity personalDataEntity) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPersonalData(personalDataEntity);
        addressEntity.setCity(DEFAULT_STRING);
        addressEntity.setCommunity(DEFAULT_STRING);
        addressEntity.setCountry(DEFAULT_STRING);
        addressEntity.setCounty(DEFAULT_STRING);
        addressEntity.setHouseNumber(DEFAULT_STRING);
        addressEntity.setPostCode(DEFAULT_POST_CODE);
        addressEntity.setStreet(DEFAULT_STRING);
        addressEntity.setVoivodeship(DEFAULT_STRING);
        addressEntity.setAddressType(DEFAULT_ADDRESS_TYPE);
        addressEntity.setStreetNumber(11);
        return addressRepository.saveAndFlush(addressEntity);
    }


    private UserEntity createUserEntity() {
        return userRepository.saveAndFlush(authProvider.createNewUserEntity());
    }

}
