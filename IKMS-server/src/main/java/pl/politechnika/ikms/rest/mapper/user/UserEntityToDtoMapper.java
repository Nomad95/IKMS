package pl.politechnika.ikms.rest.mapper.user;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.service.UserService;


@Data
@Component
@Slf4j
public class UserEntityToDtoMapper extends AbstractModelMapper<User,UserDto> {

    private final @NonNull UserService userService;

    @Autowired
    public UserEntityToDtoMapper(
            ModelMapper modelMapper,
            UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User entity = modelMapper.map(userDto, User.class);
        //find existing user and find his role and password
        log.info("Fetching user role and password");
        User user = userService.getUserByUsername(entity.getUsername());
        entity.setRole(user.getRole());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
