package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;

@Component
public class ParentEntityMapper extends AbstractModelMapper<ParentEntity,ParentDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;

    public ParentEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public ParentDto convertToDto(ParentEntity parentEntity) {
        ParentDto parentDto = modelMapper.map(parentEntity, ParentDto.class);
        parentDto.setPersonalData(new MinimalDto<>(
                parentEntity.getPersonalData().getId(),
                parentEntity.getPersonalData().getSurname()));
        return parentDto;
    }

    @Override
    public ParentEntity convertToEntity(ParentDto parentDto) {
        ParentEntity parentEntity = modelMapper.map(parentDto, ParentEntity.class);
        parentEntity.setPersonalData(personalDataRepository.findOne(parentDto.getPersonalData().getId()));
        return parentEntity;
    }
}
