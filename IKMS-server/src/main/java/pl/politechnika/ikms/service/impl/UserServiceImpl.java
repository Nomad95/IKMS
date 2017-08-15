package pl.politechnika.ikms.service.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<User,UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository, User.class);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(getRepository().findByUsername(username));
        return user.orElseThrow(()-> new EntityNotFoundException("Nie znaleziono użytkownika o loginie: "+username));
    }
}
