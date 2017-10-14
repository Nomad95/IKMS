package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;

@Component
public class ChildEntityMapper extends AbstractModelMapper<ChildEntity,ChildDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;

    public ChildEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public ChildDto convertToDto(ChildEntity childEntity) {
        ChildDto childDto = modelMapper.map(childEntity, ChildDto.class);
        childDto.setPersonalData(new MinimalDto<>(childEntity.getPersonalData().getId(),childEntity.getPersonalData().getPesel()));
        return childDto;
    }

    @Override
    public ChildEntity convertToEntity(ChildDto childDto) {
        ChildEntity childEntity = modelMapper.map(childDto, ChildEntity.class);
        childEntity.setPersonalData(personalDataRepository.findOne(childDto.getPersonalData().getId()));
        return childEntity;
    }
}
