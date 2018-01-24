package pl.politechnika.ikms.service.person;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.person.AddressDto;

import java.util.List;

public interface AddressService extends GenericService<AddressDto>{

    List<AddressDto> findByPersonalDataId(Long id);

    List<AddressDto> findByParentId(Long id);
}
