package pl.politechnika.ikms.service.person.impl;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeGeneralDetailDto;
import pl.politechnika.ikms.rest.dto.person.PhoneNumberDto;
import pl.politechnika.ikms.rest.mapper.person.EmployeeEntityMapper;
import pl.politechnika.ikms.service.person.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl extends AbstractService<EmployeeEntity,EmployeeRepository> implements EmployeeService {

    private final @NonNull EmployeeEntityMapper employeeEntityMapper;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeEntityMapper employeeEntityMapper) {
        super(repository, EmployeeEntity.class);
        this.employeeEntityMapper = employeeEntityMapper;
    }

    @Override
    public Page<EmployeeGeneralDetailDto> getEmployeesGeneralDetails(Pageable pageable) {
        Page<EmployeeEntity> employees = findAllPaginated(pageable);
        return employees.map(e -> EmployeeEntityMapper.convertToGeneralDetail(e, e.getPersonalData(),e.getPersonalData().getUser()));
    }

    @Override
    public List<MinimalDto<Long, String>> getEmployeesMinimal() {
        return getRepository().getEmployeesMinimal();
    }

    @Override
    public List<PhoneNumberDto> getEmployeesPhoneNumbers() {
        return getRepository().findAll().stream()
                .filter( employee -> (!StringUtils.isEmpty(employee.getPersonalData().getContactNumber())))
                .map(employeeEntityMapper::convertToPhoneNumber)
                .collect(Collectors.toList());
    }
}
