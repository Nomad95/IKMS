package pl.politechnika.ikms.commons.abstracts;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public abstract class AbstractModelMapper<ENTITY, DTO> implements MapperInterface<ENTITY, DTO> {
    protected final @NonNull ModelMapper modelMapper;
}
