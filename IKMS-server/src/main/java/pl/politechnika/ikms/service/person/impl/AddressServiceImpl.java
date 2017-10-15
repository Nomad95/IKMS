package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.repository.person.AddressRepository;
import pl.politechnika.ikms.service.person.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl extends AbstractService<AddressEntity,AddressRepository> implements AddressService {

    public AddressServiceImpl(AddressRepository repository) {
        super(repository,AddressEntity.class);
    }

    @Override
    public List<AddressEntity> findByPersonalDataId(Long id) {
        return getRepository().findByPersonalDataId(id);
    }
}
