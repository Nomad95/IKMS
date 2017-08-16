package pl.politechnika.ikms.commons.abstracts;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Extend all service impl classes with this abstract - provides standard crud operations
 * @param <T> Entity
 * @param <R> Repository
 */
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractService<T extends AbstractEntity, R extends JpaRepository<T,Long>> implements GenericService<T> {

    private final R repository;
    private Class<T> clazz;

    @Override
    public T findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(T entity) {
        T found = repository.findOne(entity.getId());
        if (found == null) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + entity.getId());
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        T one = repository.findOne(id);
        if (one == null) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + id);
        repository.delete(id);
    }

    @Override
    public void delete(T entity) {
        T one = repository.findOne(entity.getId());
        if (one == null) throw new EntityNotFoundException(
                "Nie znaleziono obiektu " + clazz.getSimpleName() + " o identyfikatorze " + entity.getId());
        repository.delete(entity);
    }

    public R getRepository(){
        return this.repository;
    }
}
