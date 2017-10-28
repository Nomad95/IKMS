package pl.politechnika.ikms.rest.mapper.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class UserEntityRegistrationMapper extends AbstractModelMapper<UserEntity,UserRegistrationDto> {

    private final @NonNull RoleEnumToRoleEntityMapper roleMapper;

    @Autowired
    public UserEntityRegistrationMapper(ModelMapper modelMapper, RoleEnumToRoleEntityMapper roleMapper) {
        super(modelMapper);
        this.roleMapper = roleMapper;
    }

    /**
     * @throws NotImplementedException because we have no use in this method
     */
    @Override
    public UserRegistrationDto convertToDto(UserEntity user) {
        throw new NotImplementedException();
    }

    @Override
    public UserEntity convertToEntity(UserRegistrationDto userRegistrationDto) {
        UserEntity entity = modelMapper.map(userRegistrationDto, UserEntity.class);
        //hash the password
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode("user"));

        entity.setCreatedDate(new Date());
        entity.setRole(roleMapper.getRoleFromEnum(userRegistrationDto.getRole()));
        entity.setEnabled(true);
        return entity;
    }
}
