package pl.politechnika.ikms.service.register.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.register.RegisterEntity;
import pl.politechnika.ikms.domain.register.RegisterEntryEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.register.RegisterRepository;
import pl.politechnika.ikms.rest.dto.register.RegisterDto;
import pl.politechnika.ikms.rest.dto.register.RegisterEntryDto;
import pl.politechnika.ikms.rest.dto.register.search.RegistrySearchCriteria;
import pl.politechnika.ikms.rest.mapper.register.RegisterEntityMapper;
import pl.politechnika.ikms.rest.mapper.register.RegisterEntryEntityMapper;
import pl.politechnika.ikms.security.JwtUserFacilities;
import pl.politechnika.ikms.service.register.RegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Service
@Transactional
public class RegisterServiceImpl
        extends AbstractService<RegisterEntity, RegisterDto, RegisterRepository, RegisterEntityMapper>
        implements RegisterService {

    private final @NonNull
    JwtUserFacilities jwtUserFacilities;
    private final RegisterEntryEntityMapper registerEntryEntityMapper;

    private final @NonNull
    EmployeeRepository employeeRepository;

    public RegisterServiceImpl(RegisterRepository repository, JwtUserFacilities jwtUserFacilities,
            EmployeeRepository employeeRepository, RegisterEntityMapper converter,
            RegisterEntryEntityMapper registerEntryEntityMapper) {
        super(repository, converter, RegisterEntity.class);
        this.jwtUserFacilities = jwtUserFacilities;
        this.employeeRepository = employeeRepository;
        this.registerEntryEntityMapper = registerEntryEntityMapper;
    }

    @Override
    public RegisterDto create(RegisterDto registerDto, Clock clock, HttpServletRequest request) {
        RegisterEntity registerToCreate = getConverter().convertToEntity(registerDto);

        EmployeeEntity me = employeeRepository
                .getByUserId(jwtUserFacilities.findUserByUsernameFromToken(request).getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee from token does not exist"));

        registerToCreate.getPresences()
                .forEach(presence -> {
                    presence.setCheckingTime(LocalDateTime.now(clock));
                    presence.setCheckingPerson(me);
                });
        RegisterEntity savedEntity = getRepository().save(registerToCreate);

        return getConverter().convertToDto(savedEntity);
    }

    @Override
    public RegisterDto addEntry(RegisterEntryDto registerEntryDto, Clock clock, HttpServletRequest request) {
        RegisterEntity registerEntity = getRepository().findOne(registerEntryDto.getRegisterId());
        RegisterEntryEntity registerEntryEntity = registerEntryEntityMapper.convertToEntity(registerEntryDto);

        EmployeeEntity me = employeeRepository
                .getByUserId(jwtUserFacilities.findUserByUsernameFromToken(request).getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee from token does not exist"));
        registerEntryEntity.setEmployee(me);

        if (nonNull(registerEntity)) {
            registerEntity.getEntries().add(registerEntryEntity);
        }

        getRepository().save(registerEntity);

        return getConverter().convertToDto(registerEntity);
    }

    @Override
    public RegisterDto getRegistryByCriteria(RegistrySearchCriteria registrySearchCriteria) {

        return null;
    }
}
