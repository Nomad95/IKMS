package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;

@Component
public class EmployeeEntityMapper extends AbstractModelMapper<EmployeeEntity,EmployeeDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;

    @Autowired
    public EmployeeEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public EmployeeDto convertToDto(EmployeeEntity employeeEntity) {
        System.out.println(employeeEntity);
        EmployeeDto employeeDto = modelMapper.map(employeeEntity, EmployeeDto.class);
        employeeDto.setPersonalData(new MinimalDto<>(
                employeeEntity.getPersonalData().getId(),
                employeeEntity.getPersonalData().getSurname()));
        return employeeDto;
    }

    @Override
    public EmployeeEntity convertToEntity(EmployeeDto employeeDto) {
        EmployeeEntity entity = modelMapper.map(employeeDto, EmployeeEntity.class);
        entity.setPersonalData(personalDataRepository.findOne(employeeDto.getPersonalData().getId()));
        return entity;
    }
}
