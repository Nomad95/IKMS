package pl.politechnika.ikms.service;

import pl.politechnika.ikms.domain.user.User;

public interface UserService {
    User getUser(Long id);
}
