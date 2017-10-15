package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;

@Component
public class ChildEntityMapper extends AbstractModelMapper<ChildEntity,ChildDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;
    private final @NonNull ParentRepository parentRepository;

    public ChildEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository, ParentRepository parentRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
        this.parentRepository = parentRepository;
    }

    @Override
    public ChildDto convertToDto(ChildEntity childEntity) {
        ChildDto childDto = modelMapper.map(childEntity, ChildDto.class);
        childDto.setPersonalData(new MinimalDto<>(childEntity.getPersonalData().getId(),childEntity.getPersonalData().getPesel()));
        childDto.setParent(new MinimalDto<>(childEntity.getParent().getId(),""));
        return childDto;
    }

    @Override
    public ChildEntity convertToEntity(ChildDto childDto) {
        ChildEntity childEntity = modelMapper.map(childDto, ChildEntity.class);
        childEntity.setPersonalData(personalDataRepository.findOne(childDto.getPersonalData().getId()));
        childEntity.setParent(parentRepository.findOne(childDto.getParent().getId()));

        return childEntity;
    }

    public ChildGeneralDetailDto convertToGeneralDetail(ChildEntity childEntity){
        ChildGeneralDetailDto childGeneralDetailDto = new ChildGeneralDetailDto();
        childGeneralDetailDto.setId(childEntity.getId());
        childGeneralDetailDto.setName(childEntity.getPersonalData().getName());
        childGeneralDetailDto.setSurname(childEntity.getPersonalData().getSurname());
        childGeneralDetailDto.setParent(new MinimalDto<>(
                childEntity.getParent().getId(),
                childEntity.getParent().getPersonalData().getName() + " "
                        + childEntity.getParent().getPersonalData().getSurname()));
        childGeneralDetailDto.setPersonalData(new MinimalDto<>(childEntity.getPersonalData().getId(),""));

        return childGeneralDetailDto;
    }
}
