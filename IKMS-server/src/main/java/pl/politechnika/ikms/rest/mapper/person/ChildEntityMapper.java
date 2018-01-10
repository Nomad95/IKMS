package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;

import java.util.Objects;

@Component
public class ChildEntityMapper extends AbstractModelMapper<ChildEntity,ChildDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;
    private final @NonNull ParentRepository parentRepository;
    private final @NonNull GroupRepository groupRepository;

    public ChildEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository, ParentRepository parentRepository, GroupRepository groupRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
        this.parentRepository = parentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ChildDto convertToDto(ChildEntity childEntity) {
        ChildDto childDto = modelMapper.map(childEntity, ChildDto.class);

        if (Objects.nonNull(childEntity.getGroup())) {
            childDto.setGroup(new MinimalDto<>(childEntity.getGroup().getId(), childEntity.getGroup().getName()));
        }

        if (Objects.nonNull(childEntity.getParent())) {
            childDto.setParent(new MinimalDto<>(childEntity.getParent().getId(),
                    childEntity.getParent().getPersonalData().getName() + " " + childEntity.getParent().getPersonalData().getSurname()));
            childDto.setParentUsername(childEntity.getParent().getPersonalData().getUser().getUsername());
        }

        if (Objects.nonNull(childEntity.getPersonalData())) {
            childDto.setPersonalData(new MinimalDto<>(childEntity.getPersonalData().getId(), childEntity.getPersonalData().getName() + " " + childEntity.getPersonalData().getSurname() ));
        }

        return childDto;
    }

    @Override
    public ChildEntity convertToEntity(ChildDto childDto) {
        ChildEntity childEntity = modelMapper.map(childDto, ChildEntity.class);
        childEntity.setPersonalData(personalDataRepository.findOne(childDto.getPersonalData().getId()));
        childEntity.setParent(parentRepository.findOne(childDto.getParent().getId()));

        if(Objects.nonNull(childDto.getGroup()))
            childEntity.setGroup(groupRepository.findOne(childDto.getGroup().getId()));

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
