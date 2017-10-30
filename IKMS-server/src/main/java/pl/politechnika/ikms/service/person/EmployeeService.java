package pl.politechnika.ikms.service.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeGeneralDetailDto;

import java.util.List;

public interface EmployeeService extends GenericService<EmployeeEntity> {

    Page<EmployeeGeneralDetailDto> getEmployeesGeneralDetails(Pageable pageable);

    List<MinimalDto<Long, String>> getEmployeesMinimal();
}
