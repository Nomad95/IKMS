package pl.politechnika.ikms.commons.abstracts;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

/**
 * Abstract sheet class for object mapping
 * @param <ENTITY> entity class
 * @param <DTO> dto class
 */
@RequiredArgsConstructor
public abstract class AbstractModelMapper<ENTITY, DTO> implements MapperInterface<ENTITY, DTO> {
    protected final @NonNull ModelMapper modelMapper;
}
