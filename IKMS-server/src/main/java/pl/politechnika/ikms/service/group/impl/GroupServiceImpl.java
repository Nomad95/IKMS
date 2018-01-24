package pl.politechnika.ikms.service.group.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.group.GroupDto;
import pl.politechnika.ikms.rest.mapper.group.GroupEntityMapper;
import pl.politechnika.ikms.service.group.GroupService;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GroupServiceImpl extends AbstractService<GroupEntity, GroupDto, GroupRepository, GroupEntityMapper> implements GroupService{

    private final @NonNull ChildRepository childRepository;

    public GroupServiceImpl(GroupRepository repository, ChildRepository childRepository, GroupEntityMapper groupEntityMapper) {
        super(repository, groupEntityMapper, GroupEntity.class);
        this.childRepository = childRepository;
    }

    @Override
    @Transactional
    public GroupDto update(GroupDto dto) {
        GroupEntity foundEntity = getRepository().findOne(dto.getId());
        if (Objects.isNull(foundEntity)) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + GroupEntity.class.getSimpleName() + " o identyfikatorze " + dto.getId());

        GroupEntity groupToUpdate = getConverter().convertToEntity(dto);

        List<ChildEntity> children = foundEntity.getChildren();
        children.removeAll(groupToUpdate.getChildren());
        children.forEach(c->c.setGroup(null));
        childRepository.save(children);

        return getConverter().convertToDto(getRepository().save(groupToUpdate));
    }

    @Override
    public List<MinimalDto<Long, String>> getGroupsMinimal() {
        return getRepository().getGroupsMinimal();
    }
}
