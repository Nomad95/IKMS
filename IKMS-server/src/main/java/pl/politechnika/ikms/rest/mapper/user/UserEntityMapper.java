package pl.politechnika.ikms.rest.mapper.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.service.user.UserService;


@Component
@Slf4j
public class UserEntityMapper extends AbstractModelMapper<UserEntity,UserDto> {

    private final @NonNull UserService userService;

    @Autowired
    public UserEntityMapper(
            ModelMapper modelMapper,
            UserService userService) {
        super(modelMapper);
        this.userService = userService;
    }

    @Override
    public UserDto convertToDto(UserEntity user) {
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserEntity convertToEntity(UserDto userDto) {
        UserEntity entity = modelMapper.map(userDto, UserEntity.class);
        //find existing user and find his role and password
        log.info("Fetching user role and password");
        UserEntity user = userService.getUserByUsername(entity.getUsername());
        entity.setRole(user.getRole());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
