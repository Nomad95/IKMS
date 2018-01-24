package pl.politechnika.ikms.service.user.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.configuration.ApplicationConstants;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.role.RoleDto;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityMapper;
import pl.politechnika.ikms.rest.mapper.user.UserEntityRegistrationMapper;
import pl.politechnika.ikms.service.user.UserService;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserEntity, UserDto, UserRepository, UserEntityMapper>
        implements UserService {

    private final UserEntityRegistrationMapper userEntityRegistrationMapper;

    public UserServiceImpl(UserRepository repository, UserEntityMapper converter,
            UserEntityRegistrationMapper userEntityRegistrationMapper) {
        super(repository, converter, UserEntity.class);
        this.userEntityRegistrationMapper = userEntityRegistrationMapper;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<UserEntity> user = Optional.ofNullable(getRepository().findByUsername(username));
        UserEntity userEntity = user
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o loginie: " + username));
        return getConverter().convertToDto(userEntity);
    }

    public UserDto create(UserRegistrationDto dto) {
        UserEntity userEntity = userEntityRegistrationMapper.convertToEntity(dto);
        UserEntity savedEntity = getRepository().save(userEntity);
        return getConverter().convertToDto(savedEntity);
    }

    @Override
    @Cacheable(value = ApplicationConstants.ROLE_CACHE)
    public RoleDto getRoleByUsername(String username) {
        return getRepository().getRoleByUsername(username);
    }
}
