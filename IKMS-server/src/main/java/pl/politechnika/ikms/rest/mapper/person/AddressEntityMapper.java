package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.AddressEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.AddressDto;

@Component
public class AddressEntityMapper extends AbstractModelMapper<AddressEntity,AddressDto> {

    private final @NonNull UserRepository userRepository;

    public AddressEntityMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public AddressDto convertToDto(AddressEntity addressEntity) {
        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
        addressDto.setUser(new MinimalDto<>(addressEntity.getUser().getId(),addressEntity.getUser().getUsername()));
        return addressDto;
    }

    @Override
    public AddressEntity convertToEntity(AddressDto addressDto) {
        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
        addressEntity.setUser(userRepository.findOne(addressDto.getUser().getId()));
        return addressEntity;
    }
}
