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
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;
import pl.politechnika.ikms.rest.mapper.person.EmployeeEntityMapper;
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
public class EmployeeControllerTest {

    private static final String DEFAULT_NIP = "123-231-31-34";
    private static final String UPDATED_NIP = "321-231-99-77";
    private static final EmployeeRole DEFAULT_ROLE = EmployeeRole.BABYSITTER;
    private static final EmployeeRole UPDATED_ROLE = EmployeeRole.SPEECH_TERAPIST;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;

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
    public void getCreatedEmployee() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        EmployeeEntity savedEmployee = createEmployee(personalDataEntity);

        mockMvc.perform(
                get("/api/employee/" + savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.nip",is(DEFAULT_NIP)));

    }

    @Test
    @Transactional
    @Rollback
    public void createEmployee() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        EmployeeDto employeeDto = createEmployeeDto(personalDataEntity);

        mockMvc.perform(
                post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.nip",is(DEFAULT_NIP)))
                .andExpect(jsonPath("$.employeeRole",is(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    @Rollback
    public void updateEmployee() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        EmployeeEntity savedEmployee = createEmployee(personalDataEntity);

        savedEmployee.setEmployeeRole(UPDATED_ROLE);
        savedEmployee.setNip(UPDATED_NIP);
        EmployeeDto employeeDto = employeeEntityMapper.convertToDto(savedEmployee);

        mockMvc.perform(
                put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token)
                        .content(jacksonObjectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalData.id", is(personalDataEntity.getId().intValue())))
                .andExpect(jsonPath("$.nip",is(UPDATED_NIP)))
                .andExpect(jsonPath("$.employeeRole",is(UPDATED_ROLE.toString())));
    }

    @Test
    @Transactional
    @Rollback
    public void getAllEmployees() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        createEmployee(personalDataEntity);

        mockMvc.perform(
                get("/api/employee")
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
    public void deleteEmployee() throws Exception {
        //create all necessary data and Employee
        token = authProvider.generateTokenForUser(authProvider.createNewSimpleUser());
        UserEntity userEntity = createUserEntity();
        PersonalDataEntity personalDataEntity = PersonalDataControllerTest.createPersonalDataEntity(userEntity, personalDataRepository);

        EmployeeEntity employee = createEmployee(personalDataEntity);

        //check employee was deleted but personal data remains
        int sizeBefore = employeeRepository.findAll().size();
        int personalDataSizeBefore = personalDataRepository.findAll().size();

        mockMvc.perform(
                delete("/api/employee/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Auth-token",token))
                .andExpect(status().isOk());

        int sizeAfter = employeeRepository.findAll().size();
        int personalDataSizeAfter = personalDataRepository.findAll().size();

        Assert.assertThat(sizeAfter,is(sizeBefore - 1));
        Assert.assertThat(personalDataSizeAfter,is(personalDataSizeBefore));
    }


    private EmployeeDto createEmployeeDto(PersonalDataEntity personalDataEntity) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setPersonalData(new MinimalDto<>(personalDataEntity.getId(),personalDataEntity.getName()));
        employeeDto.setEmployeeRole(DEFAULT_ROLE);
        employeeDto.setNip(DEFAULT_NIP);
        return employeeDto;
    }

    private EmployeeEntity createEmployee(PersonalDataEntity personalDataEntity) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setPersonalData(personalDataEntity);
        employeeEntity.setNip(DEFAULT_NIP);
        employeeEntity.setEmployeeRole(DEFAULT_ROLE);
        return employeeRepository.saveAndFlush(employeeEntity);
    }

    private UserEntity createUserEntity() {
        return userRepository.saveAndFlush(authProvider.createNewUserEntity());
    }
}
