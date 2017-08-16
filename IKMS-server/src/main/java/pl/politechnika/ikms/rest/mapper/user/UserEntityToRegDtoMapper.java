package pl.politechnika.ikms.rest.mapper.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final @NonNull RoleEnumToRoleEntityMapper roleMapper;

    @Autowired
    public UserEntityToRegDtoMapper(ModelMapper modelMapper, RoleEnumToRoleEntityMapper roleMapper) {
        super(modelMapper);
        this.roleMapper = roleMapper;
    }

    /**
     * @throws NotImplementedException because we have no use in this method
     */
    @Override
    public UserRegistrationDto convertToDto(User user) {
        throw new NotImplementedException();
    }

    @Override
    public User convertToEntity(UserRegistrationDto userRegistrationDto) {
        User entity = modelMapper.map(userRegistrationDto, User.class);
        //hash the password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));

        entity.setCreatedDate(new Date());
        entity.setRole(roleMapper.getRoleFromEnum(userRegistrationDto.getRole()));
        entity.setEnabled(true);
        return entity;
    }
}
