package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;

@Component
public class EmployeeEntityMapper extends AbstractModelMapper<EmployeeEntity,EmployeeDto> {

    private final @NonNull UserRepository userRepository;

    @Autowired
    public EmployeeEntityMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public EmployeeDto convertToDto(EmployeeEntity employeeEntity) {
        System.out.println(employeeEntity);
        EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);
        employeeDto.setUser(new MinimalDto<>(employeeEntity.getUserEntity().getId(),employeeEntity.getUserEntity().getUsername()));
        return employeeDto;
    }

    @Override
    public EmployeeEntity convertToEntity(EmployeeDto employeeDto) {
        EmployeeEntity entity = modelMapper.map(employeeDto, EmployeeEntity.class);
        entity.setUserEntity(userRepository.findOne(employeeDto.getUser().getId()));
        return entity;
    }
}
