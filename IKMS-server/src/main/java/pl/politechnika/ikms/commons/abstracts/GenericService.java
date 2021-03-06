package pl.politechnika.ikms.commons.abstracts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<T extends AbstractEntity> {

    T findOne(final Long id);

    List<T> findAll();

    Page<T> findAllPaginated(Pageable pageable);

    T create(T entity);

    T update(T entity);

    void deleteById(final Long id);

    void delete(final T entity);
}
