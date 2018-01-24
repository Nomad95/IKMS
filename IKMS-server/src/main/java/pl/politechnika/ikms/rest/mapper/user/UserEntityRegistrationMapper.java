package pl.politechnika.ikms.rest.mapper.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
    private final @NonNull JavaMailSender mailSender;

    @Autowired
    public UserEntityRegistrationMapper(ModelMapper modelMapper, RoleEnumToRoleEntityMapper roleMapper, JavaMailSenderImpl mailSender) {
        super(modelMapper);
        this.roleMapper = roleMapper;
        this.mailSender = mailSender;
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

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));

        entity.setCreatedDate(new Date());
        entity.setRole(roleMapper.getRoleFromEnum(userRegistrationDto.getRole()));
        entity.setEnabled(true);
        return entity;
    }

}
