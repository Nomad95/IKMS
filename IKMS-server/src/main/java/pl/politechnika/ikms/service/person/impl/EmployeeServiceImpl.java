package pl.politechnika.ikms.service.person.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.service.person.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl extends AbstractService<EmployeeEntity,EmployeeRepository> implements EmployeeService {

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository, EmployeeEntity.class);
    }
}
