package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;

@Component
public class PersonalDataEntityMapper extends AbstractModelMapper<PersonalDataEntity, PersonalDataDto> {

    private final @NonNull UserRepository userRepository;

    public PersonalDataEntityMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public PersonalDataDto convertToDto(PersonalDataEntity personalDataEntity) {
        PersonalDataDto personalDataDto = modelMapper.map(personalDataEntity, PersonalDataDto.class);
        personalDataDto.setUser(new MinimalDto<>(
                personalDataEntity.getUser().getId(),
                personalDataEntity.getUser().getUsername()
        ));
        return personalDataDto;
    }

    @Override
    public PersonalDataEntity convertToEntity(PersonalDataDto personalDataDto) {
        PersonalDataEntity personalDataEntity = modelMapper.map(personalDataDto, PersonalDataEntity.class);
        personalDataEntity.setUser(userRepository.findOne(personalDataDto.getUser().getId()));
        return personalDataEntity;
    }
}
