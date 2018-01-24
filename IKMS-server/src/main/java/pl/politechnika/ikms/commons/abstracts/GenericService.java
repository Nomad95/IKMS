package pl.politechnika.ikms.commons.abstracts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenericService<DTO extends AbstractDto> {

    DTO findOne(final Long id);

    List<DTO> findAll();

    Page<DTO> findAllPaginated(Pageable pageable);

    DTO create(DTO dto);

    DTO update(DTO dto);

    void deleteById(final Long id);

    void delete(final DTO dto);
}
