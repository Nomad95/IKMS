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
import pl.politechnika.ikms.service.group.GroupService;

import java.util.List;

@Service
@Slf4j
public class GroupServiceImpl extends AbstractService<GroupEntity, GroupRepository> implements GroupService{

    private final @NonNull ChildRepository childRepository;

    public GroupServiceImpl(GroupRepository repository, ChildRepository childRepository) {
        super(repository,GroupEntity.class);
        this.childRepository = childRepository;
    }

    @Override
    @Transactional
    public GroupEntity update(GroupEntity entity) {
        GroupEntity foundEntity = getRepository().findOne(entity.getId());
        if (foundEntity == null) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + GroupEntity.class.getSimpleName() + " o identyfikatorze " + entity.getId());

        List<ChildEntity> children = foundEntity.getChildren();
        children.removeAll(entity.getChildren());
        children.forEach(c->c.setGroup(null));
        childRepository.save(children);

        return getRepository().save(entity);
    }
}
