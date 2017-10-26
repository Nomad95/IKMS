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
import pl.politechnika.ikms.service.user.UserService;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserEntity,UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository, UserEntity.class);
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        Optional<UserEntity> user = Optional.ofNullable(getRepository().findByUsername(username));
        return user.orElseThrow(()-> new EntityNotFoundException("Nie znaleziono użytkownika o loginie: "+username));
    }

    @Override
    @Cacheable(value = ApplicationConstants.ROLE_CACHE)
    public RoleDto getRoleByUsername(String username) {
        return getRepository().getRoleByUsername(username);
    }
}
