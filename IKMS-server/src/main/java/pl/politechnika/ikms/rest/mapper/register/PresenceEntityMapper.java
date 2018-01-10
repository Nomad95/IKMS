package pl.politechnika.ikms.rest.mapper.register;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.register.PresenceEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.register.PresenceDto;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Component
public class PresenceEntityMapper extends AbstractModelMapper<PresenceEntity, PresenceDto> {

    private final @NonNull
    ChildRepository childRepository;
    private final @NonNull
    EmployeeRepository employeeRepository;

    public PresenceEntityMapper(ModelMapper modelMapper,
                                ChildRepository childRepository,
                                EmployeeRepository employeeRepository) {
        super(modelMapper);
        this.childRepository = childRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public PresenceDto convertToDto(PresenceEntity presenceEntity) {
        checkArgument(nonNull(presenceEntity), "Expected non-null presenceEntity");
       // checkArgument(nonNull(presenceEntity.getId()), "Expected non-null id of registerEntity");

        PresenceDto presenceDto = modelMapper.map(presenceEntity, PresenceDto.class);

        if (nonNull(presenceEntity.getChild())){
            presenceDto.setChild(
                    new MinimalDto<>(
                            presenceEntity.getId(),
                            format("%s %s",
                                    presenceEntity.getChild().getPersonalData().getName(),
                                    presenceEntity.getChild().getPersonalData().getSurname())));
        }
        if (nonNull(presenceEntity.getCheckingPerson())){
            presenceDto.setCheckingPerson(
                    new MinimalDto<>(
                            presenceEntity.getId(),
                            format("%s %s",
                                    presenceEntity.getCheckingPerson().getPersonalData().getName(),
                                    presenceEntity.getCheckingPerson().getPersonalData().getSurname())));
        }

        return presenceDto;
    }

    @Override
    public PresenceEntity convertToEntity(PresenceDto presenceDto) {
        checkArgument(nonNull(presenceDto), "Expected non-null presenceEntity");

        PresenceEntity presenceEntity = modelMapper.map(presenceDto, PresenceEntity.class);

        if (nonNull(presenceDto.getChild())){
            presenceEntity.setChild(childRepository.findOne(presenceDto.getChild().getId()));
        }
        if (nonNull(presenceDto.getCheckingPerson())){
           presenceEntity.setCheckingPerson(employeeRepository.findOne(presenceDto.getCheckingPerson().getId()));
        }

         return presenceEntity;
    }
}
