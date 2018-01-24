package pl.politechnika.ikms.rest.mapper.user;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.user.UserDto;


@Component
@Slf4j
public class UserEntityMapper extends AbstractModelMapper<UserEntity, UserDto> {

    private final UserRepository userRepository;

    @Autowired
    public UserEntityMapper(
            ModelMapper modelMapper,
            UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public UserDto convertToDto(UserEntity user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserEntity convertToEntity(UserDto userDto) {
        UserEntity entity = modelMapper.map(userDto, UserEntity.class);
        //find existing user and find his role and password
        log.info("Fetching user role and password");
        UserEntity user = userRepository.findByUsername(entity.getUsername());
        entity.setRole(user.getRole());
        entity.setPassword(user.getPassword());
        return entity;
    }
}
