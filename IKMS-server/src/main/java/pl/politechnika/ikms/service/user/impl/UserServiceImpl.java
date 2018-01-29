package pl.politechnika.ikms.service.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.commons.util.RandomString;
import pl.politechnika.ikms.configuration.ApplicationConstants;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;
import pl.politechnika.ikms.rest.dto.role.RoleDto;
import pl.politechnika.ikms.rest.dto.user.RegistrationDto;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityMapper;
import pl.politechnika.ikms.rest.mapper.user.UserEntityRegistrationMapper;
import pl.politechnika.ikms.service.person.AddressService;
import pl.politechnika.ikms.service.person.EmployeeService;
import pl.politechnika.ikms.service.person.ParentService;
import pl.politechnika.ikms.service.person.PersonalDataService;
import pl.politechnika.ikms.service.user.UserService;

import javax.validation.*;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserEntity, UserDto, UserRepository, UserEntityMapper>
        implements UserService {

    private final UserEntityRegistrationMapper userEntityRegistrationMapper;
    private final PersonalDataService personalDataService;
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final ParentService parentService;

    public UserServiceImpl(UserRepository repository, UserEntityMapper converter,
            UserEntityRegistrationMapper userEntityRegistrationMapper,
            PersonalDataService personalDataService,
            AddressService addressService, EmployeeService employeeService,
            ParentService parentService) {
        super(repository, converter, UserEntity.class);
        this.userEntityRegistrationMapper = userEntityRegistrationMapper;
        this.personalDataService = personalDataService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.parentService = parentService;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<UserEntity> user = Optional.ofNullable(getRepository().findByUsername(username));
        UserEntity userEntity = user
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o loginie: " + username));
        return getConverter().convertToDto(userEntity);
    }

    @Transactional
    public UserDto create(RegistrationDto dto) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        dto.getUserRegistrationDto().setPassword(new RandomString(8, ThreadLocalRandom.current()).nextString());
        dto.getUserRegistrationDto().setUsername(new RandomString(8, ThreadLocalRandom.current()).nextString());

        Set<ConstraintViolation<UserRegistrationDto>> userInvalidData = validator.validate(dto.getUserRegistrationDto());
        if(!CollectionUtils.isEmpty(userInvalidData)) {
            log.error("UserRegistrationDto has invalid data: {}", userInvalidData);
            throw new ValidationException("User data is not valid");
        }

        if(!CollectionUtils.isEmpty(dto.getAddressess())) {
            for (AddressDto addressDto : dto.getAddressess()) {
                Set<ConstraintViolation<AddressDto>> addressInvalidData = validator.validate(addressDto);
                if(!CollectionUtils.isEmpty(addressInvalidData)) {
                    log.error("Address has invalid data: {}", addressInvalidData);
                    throw new ValidationException("Address data is not valid");
                }
            }
        }

        Set<ConstraintViolation<PersonalDataDto>> personalDataInvalidData = validator.validate(dto.getPersonalData());
        if(!CollectionUtils.isEmpty(personalDataInvalidData)) {
            log.error("Personal data has invalid data: {}", personalDataInvalidData);
            throw new ValidationException("PersonalData data is not valid");
        }

        if(Objects.nonNull(dto.getEmployee())) {
            Set<ConstraintViolation<EmployeeDto>> employeeInvalidData = validator.validate(dto.getEmployee());
            if(!CollectionUtils.isEmpty(employeeInvalidData)) {
                log.error("Employee has invalid data: {}", employeeInvalidData);
                throw new ValidationException("Employee data is not valid");
            }
        }

        if(Objects.nonNull(dto.getParent())) {
            Set<ConstraintViolation<ParentDto>> parentInvalidData = validator.validate(dto.getParent());
            if(!CollectionUtils.isEmpty(parentInvalidData)) {
                log.error("Parent has invalid data: {}", parentInvalidData);
                throw new ValidationException("Parent data is not valid");
            }
        }

        UserEntity userEntity = userEntityRegistrationMapper.convertToEntity(dto.getUserRegistrationDto());
        UserEntity savedUser = getRepository().save(userEntity);
        dto.getPersonalData().setUser(new MinimalDto<>(savedUser.getId(), ""));
        PersonalDataDto personalDataDto = personalDataService.create(dto.getPersonalData());
        for (AddressDto addressDto : dto.getAddressess()) {
            addressDto.setPersonalData(new MinimalDto<>(personalDataDto.getId(), ""));
            addressService.create(addressDto);
        }
        if(Objects.nonNull(dto.getEmployee())) {
            dto.getEmployee().setPersonalData(new MinimalDto<>(personalDataDto.getId(), ""));
            employeeService.create(dto.getEmployee());
        }
        if(Objects.nonNull(dto.getParent())) {
            dto.getEmployee().setPersonalData(new MinimalDto<>(personalDataDto.getId(), ""));
            parentService.create(dto.getParent());
        }

        log.info("User created properly");
        return getConverter().convertToDto(savedUser);
    }

    @Override
    @Cacheable(value = ApplicationConstants.ROLE_CACHE)
    public RoleDto getRoleByUsername(String username) {
        return getRepository().getRoleByUsername(username);
    }
}
