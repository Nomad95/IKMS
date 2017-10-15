package pl.politechnika.ikms.service.person;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.person.AddressEntity;

import java.util.List;

public interface AddressService extends GenericService<AddressEntity>{

    List<AddressEntity> findByPersonalDataId(Long id);
}
