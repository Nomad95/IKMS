package pl.politechnika.ikms.rest.mapper.register;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.register.RegisterEntity;
import pl.politechnika.ikms.rest.dto.register.RegisterDto;

import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

@Component
public class RegisterEntityMapper extends AbstractModelMapper<RegisterEntity, RegisterDto>{

    private final @NonNull
    PresenceEntityMapper presenceEntityMapper;
    private final @NonNull
    RegisterEntryEntityMapper registerEntryEntityMapper;

    public RegisterEntityMapper(ModelMapper modelMapper,
                                PresenceEntityMapper presenceEntityMapper,
                                RegisterEntryEntityMapper registerEntryEntityMapper
                                ) {
        super(modelMapper);
        this.presenceEntityMapper = presenceEntityMapper;
        this.registerEntryEntityMapper = registerEntryEntityMapper;

    }

    @Override
    public RegisterDto convertToDto(RegisterEntity registerEntity) {

        checkArgument(nonNull(registerEntity), "Expected non null registerEntity");
        checkArgument(nonNull(registerEntity.getId()), "Expected non-null id of registerEntity");

        RegisterDto registerDto = modelMapper.map(registerEntity, RegisterDto.class);

        if (nonNull(registerEntity.getPresences())){
            registerDto.setPresences(
                    registerEntity
                            .getPresences()
                            .stream()
                            .map(presenceEntityMapper::convertToDto)
                            .collect(Collectors.toList()));
        }
        if (nonNull(registerEntity.getEntries())){
            registerDto.setEntries(
                    registerEntity
                            .getEntries()
                            .stream()
                            .map(registerEntryEntityMapper::convertToDto)
                            .collect(Collectors.toList()));
        }

        return registerDto;
    }

    @Override
    public RegisterEntity convertToEntity(RegisterDto registerDto) {
        checkArgument(nonNull(registerDto), "Expected non null registerDto");

        RegisterEntity registerEntity = modelMapper.map(registerDto, RegisterEntity.class);

        if (nonNull(registerDto.getPresences())){
            registerEntity.setPresences(
                    registerDto
                            .getPresences()
                            .stream()
                            .map(presenceEntityMapper::convertToEntity)
                            .collect(Collectors.toList()));

        }
        if (nonNull(registerDto.getEntries())){
            registerEntity.setEntries(
                    registerDto
                            .getEntries()
                            .stream()
                            .map(registerEntryEntityMapper::convertToEntity)
                            .collect(Collectors.toList()));
        }

        return registerEntity;
    }
}
