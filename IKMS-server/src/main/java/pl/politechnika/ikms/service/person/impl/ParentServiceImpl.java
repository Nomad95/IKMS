package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.service.person.ParentService;

@Service
@Transactional
public class ParentServiceImpl extends AbstractService<ParentEntity,ParentRepository> implements ParentService {

    public ParentServiceImpl(ParentRepository repository) {
        super(repository, ParentEntity.class);
    }
}
