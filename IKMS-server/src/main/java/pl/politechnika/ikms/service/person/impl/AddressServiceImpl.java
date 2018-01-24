package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.repository.person.AddressRepository;
import pl.politechnika.ikms.rest.dto.person.AddressDto;
import pl.politechnika.ikms.rest.mapper.person.AddressEntityMapper;
import pl.politechnika.ikms.service.person.AddressService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl extends AbstractService<AddressEntity, AddressDto, AddressRepository, AddressEntityMapper> implements AddressService {

    public AddressServiceImpl(AddressRepository repository, AddressEntityMapper converter) {
        super(repository, converter, AddressEntity.class);
    }

    @Override
    public List<AddressDto> findByPersonalDataId(Long id) {
        return getRepository().findByPersonalDataId(id).stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<AddressDto> findByParentId(Long id) {
        return getRepository().findByParentId(id).stream().map(getConverter()::convertToDto).collect(Collectors.toList());
    }
}
