package pl.politechnika.ikms.commons.abstracts;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Extend all service impl classes with this abstract - provides standard crud operations
 * @param <ENTITY> Entity
 * @param <R> Repository
 */
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractService<ENTITY extends AbstractEntity, DTO extends AbstractDto,
        R extends JpaRepository<ENTITY, Long>, C extends AbstractModelMapper<ENTITY, DTO>>
        implements GenericService<DTO> {

    private final R repository;
    private final C converter;
    private Class<ENTITY> clazz;

    @Override
    public DTO findOne(Long id) {
        return converter.convertToDto(repository.findOne(id));
    }

    @Override
    public List<DTO> findAll() {
        return repository.findAll().stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Page<DTO> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(converter::convertToDto);
    }

    @Override
    public DTO create(DTO dto) {
        ENTITY savedEntity = repository.save(converter.convertToEntity(dto));
        return converter.convertToDto(savedEntity);
    }

    @Override
    public DTO update(DTO dto) {
        ENTITY found = repository.findOne(dto.getId());
        if (Objects.isNull(found)) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + dto.getId());
        return converter.convertToDto(repository.save(found));
    }

    @Override
    public void deleteById(Long id) {
        ENTITY found = repository.findOne(id);
        if (Objects.isNull(found)) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + id);
        repository.delete(id);
    }

    @Override
    public void delete(DTO dto) {
        ENTITY found = repository.findOne(dto.getId());
        if (Objects.isNull(found)) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + dto.getId());
        repository.delete(found);
    }

    public R getRepository() {
        return this.repository;
    }

    public C getConverter() {
        return this.converter;
    }
}
