package pl.politechnika.ikms.service.group.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.service.group.GroupService;

@Service
public class GroupServiceImpl extends AbstractService<GroupEntity, GroupRepository> implements GroupService{

    public GroupServiceImpl(GroupRepository repository) {
        super(repository,GroupEntity.class);
    }

}
