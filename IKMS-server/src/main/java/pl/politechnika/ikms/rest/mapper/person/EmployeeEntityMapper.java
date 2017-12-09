package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeGeneralDetailDto;
import pl.politechnika.ikms.rest.dto.person.PhoneNumberDto;

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
                employeeEntity.getPersonalData().getPesel()));
        return employeeDto;
    }

    @Override
    public EmployeeEntity convertToEntity(EmployeeDto employeeDto) {
        EmployeeEntity entity = modelMapper.map(employeeDto, EmployeeEntity.class);
        entity.setPersonalData(personalDataRepository.findOne(employeeDto.getPersonalData().getId()));
        return entity;
    }

    public static EmployeeGeneralDetailDto convertToGeneralDetail(EmployeeEntity employee, PersonalDataEntity personalData, UserEntity userEntity){
        EmployeeGeneralDetailDto employeeGeneralDetailDto = new EmployeeGeneralDetailDto();
        employeeGeneralDetailDto.setId(employee.getId());
        employeeGeneralDetailDto.setEmployeeRole(employee.getEmployeeRole());
        employeeGeneralDetailDto.setNip(employee.getNip());
        employeeGeneralDetailDto.setName(personalData.getName());
        employeeGeneralDetailDto.setSurname(personalData.getSurname());

        employeeGeneralDetailDto.setPersonalData(new MinimalDto<>(personalData.getId(),personalData.getPesel()));
        employeeGeneralDetailDto.setUser(new MinimalDto<>(userEntity.getId(),userEntity.getUsername()));

        return employeeGeneralDetailDto;
    }

    public PhoneNumberDto convertToPhoneNumber(EmployeeEntity employee){
        PhoneNumberDto phoneNumberDto = new PhoneNumberDto();
        phoneNumberDto.setFullName(employee.getPersonalData().getName() + " " + employee.getPersonalData().getSurname());
        phoneNumberDto.setPhoneNumber(employee.getPersonalData().getContactNumber());
        phoneNumberDto.setUserId(employee.getPersonalData().getUser().getId());

        return phoneNumberDto;
    }
}
