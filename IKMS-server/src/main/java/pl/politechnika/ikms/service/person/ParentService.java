package pl.politechnika.ikms.service.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;
import pl.politechnika.ikms.rest.dto.person.ParentGeneralDetailDto;

import java.util.List;

public interface ParentService extends GenericService<ParentDto>{

    Page<ParentGeneralDetailDto> getParentGeneralDetails(Pageable pageable);

    List<MinimalDto<Long, String>> getAllMinimal();

}
