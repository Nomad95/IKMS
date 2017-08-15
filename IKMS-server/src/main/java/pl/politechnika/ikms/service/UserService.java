package pl.politechnika.ikms.service;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.user.User;

public interface UserService extends GenericService<User>{
    User getUserByUsername(String username);

}
