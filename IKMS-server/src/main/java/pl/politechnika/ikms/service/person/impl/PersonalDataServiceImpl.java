package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.service.person.PersonalDataService;

@Service
public class PersonalDataServiceImpl extends AbstractService<PersonalDataEntity, PersonalDataRepository> implements PersonalDataService{

    public PersonalDataServiceImpl(PersonalDataRepository repository) {
        super(repository, PersonalDataEntity.class);
    }
}
