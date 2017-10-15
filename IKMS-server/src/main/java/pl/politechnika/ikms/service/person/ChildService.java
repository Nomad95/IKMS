package pl.politechnika.ikms.service.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;

public interface ChildService extends GenericService<ChildEntity> {

    Page<ChildGeneralDetailDto> getChildGeneralDetail(Pageable pageable);
}
