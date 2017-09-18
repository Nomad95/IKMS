package pl.politechnika.ikms.rest.mapper.person;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.repository.user.UserRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;

@Component
public class ParentEntityMapper extends AbstractModelMapper<ParentEntity,ParentDto> {

    private final @NonNull UserRepository userRepository;

    public ParentEntityMapper(ModelMapper modelMapper, UserRepository userRepository) {
        super(modelMapper);
        this.userRepository = userRepository;
    }

    @Override
    public ParentDto convertToDto(ParentEntity parentEntity) {
        ParentDto parentDto = modelMapper.map(parentEntity, ParentDto.class);
        parentDto.setUser(new MinimalDto<>(parentEntity.getUserEntity().getId(),parentEntity.getUserEntity().getUsername()));
        return parentDto;
    }

    @Override
    public ParentEntity convertToEntity(ParentDto parentDto) {
        ParentEntity parentEntity = modelMapper.map(parentDto, ParentEntity.class);
        parentEntity.setUserEntity(userRepository.findOne(parentDto.getUser().getId()));
        return parentEntity;
    }
}
