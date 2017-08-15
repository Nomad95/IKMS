package pl.politechnika.ikms.rest.mapper.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class UserEntityToRegDtoMapper extends AbstractModelMapper<User,UserRegistrationDto> {

    public UserEntityToRegDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public UserRegistrationDto convertToDto(User user) {
        throw new NotImplementedException();
    }

    @Override
    public User convertToEntity(UserRegistrationDto userRegistrationDto) {
        User entity = modelMapper.map(userRegistrationDto, User.class);
        entity.setCreatedDate(new Date());
        return entity;
    }
}
