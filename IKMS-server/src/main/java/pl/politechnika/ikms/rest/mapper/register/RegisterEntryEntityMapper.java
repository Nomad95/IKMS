package pl.politechnika.ikms.rest.mapper.register;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.register.RegisterEntryEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.register.RegisterEntryDto;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Component
public class RegisterEntryEntityMapper extends AbstractModelMapper<RegisterEntryEntity, RegisterEntryDto> {

    private final @NonNull
    ChildRepository childRepository;
    private final @NonNull
    EmployeeRepository employeeRepository;

    public RegisterEntryEntityMapper(ModelMapper modelMapper,
                                     ChildRepository childRepository,
                                     EmployeeRepository employeeRepository) {
        super(modelMapper);
        this.childRepository = childRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public RegisterEntryDto convertToDto(RegisterEntryEntity registerEntryEntity) {
        checkArgument(nonNull(registerEntryEntity), "Expected non-null registerEntryEntity");
        checkArgument(nonNull(registerEntryEntity.getId()), "Expected non-null id of registerEntryEntity");

        RegisterEntryDto registerEntryDto = modelMapper.map(registerEntryEntity, RegisterEntryDto.class);

        if (nonNull(registerEntryEntity.getChild())){
            registerEntryDto.setChild(
                    new MinimalDto<>(
                            registerEntryEntity.getId(),
                            format("%s %s",
                                    registerEntryEntity.getChild().getPersonalData().getName(),
                                    registerEntryEntity.getChild().getPersonalData().getSurname())));
        }
        if (nonNull(registerEntryEntity.getEmployee())){
            registerEntryDto.setEmployee(
                    new MinimalDto<>(
                            registerEntryEntity.getId(),
                            format("%s %s",
                                    registerEntryEntity.getEmployee().getPersonalData().getName(),
                                    registerEntryEntity.getEmployee().getPersonalData().getSurname())));
        }

        return registerEntryDto;
    }

    @Override
    public RegisterEntryEntity convertToEntity(RegisterEntryDto registerEntryDto) {
        checkArgument(nonNull(registerEntryDto), "Expected non-null registerEntryEntity");

        RegisterEntryEntity registerEntryEntity = modelMapper.map(registerEntryDto, RegisterEntryEntity.class);

        if (nonNull(registerEntryDto.getChild())){
            registerEntryEntity.setChild(childRepository.findOne(registerEntryDto.getChild().getId()));
        }
        if (nonNull(registerEntryDto.getEmployee())){
            registerEntryEntity.setEmployee(employeeRepository.findOne(registerEntryDto.getEmployee().getId()));
        }

        return registerEntryEntity;
    }
}
