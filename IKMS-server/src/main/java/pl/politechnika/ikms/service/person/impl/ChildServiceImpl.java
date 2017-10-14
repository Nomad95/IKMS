package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.service.person.ChildService;

@Service
public class ChildServiceImpl extends AbstractService<ChildEntity,ChildRepository> implements ChildService {

    public ChildServiceImpl(ChildRepository repository) {
        super(repository, ChildEntity.class);
    }
}
