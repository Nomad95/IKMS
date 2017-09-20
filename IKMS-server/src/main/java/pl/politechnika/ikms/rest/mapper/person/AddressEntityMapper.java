package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.AddressDto;

@Component
public class AddressEntityMapper extends AbstractModelMapper<AddressEntity,AddressDto> {

    private final @NonNull PersonalDataRepository personalDataRepository;

    public AddressEntityMapper(ModelMapper modelMapper, PersonalDataRepository personalDataRepository) {
        super(modelMapper);
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public AddressDto convertToDto(AddressEntity addressEntity) {
        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
        addressDto.setPersonalData(new MinimalDto<>(
                addressEntity.getPersonalData().getId(),
                addressEntity.getPersonalData().getSurname()));
        return addressDto;
    }

    @Override
    public AddressEntity convertToEntity(AddressDto addressDto) {
        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
        addressEntity.setPersonalData(personalDataRepository.findOne(addressDto.getPersonalData().getId()));
        return addressEntity;
    }
}
