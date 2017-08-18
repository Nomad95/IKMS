package pl.politechnika.ikms.commons.abstracts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends AbstractEntity> {

    T findOne(final Long id);

    List<T> findAll();

    Page<T> findAllPaginated(final int page,final int size, Optional<Sort> sort);

    T create(T entity);

    T update(T entity);

    void deleteById(final Long id);

    void delete(final T entity);
}
