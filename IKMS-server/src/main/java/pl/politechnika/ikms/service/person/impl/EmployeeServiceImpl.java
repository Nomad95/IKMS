package pl.politechnika.ikms.service.person.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.rest.dto.person.EmployeeGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.EmployeeEntityMapper;
import pl.politechnika.ikms.service.person.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl extends AbstractService<EmployeeEntity,EmployeeRepository> implements EmployeeService {

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository, EmployeeEntity.class);
    }

    @Override
    public Page<EmployeeGeneralDetailDto> getEmployeesGeneralDetails(Pageable pageable) {
        Page<EmployeeEntity> employees = findAllPaginated(pageable);
        return employees.map(e -> EmployeeEntityMapper.convertToGeneralDetail(e, e.getPersonalData(),e.getPersonalData().getUser()));
    }
}
