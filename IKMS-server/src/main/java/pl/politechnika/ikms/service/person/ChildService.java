package pl.politechnika.ikms.service.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;

import java.util.List;

public interface ChildService extends GenericService<ChildDto> {

    Page<ChildGeneralDetailDto> getChildGeneralDetail(Pageable pageable);

    List<ChildGeneralDetailDto> getGeneralDto(List<Long> childrenIds);

    List<MinimalDto<Long, String>> getGrouplessChildrenMinimal();

    List<MinimalDto<Long,String>> getChildrenMinimal();
}
