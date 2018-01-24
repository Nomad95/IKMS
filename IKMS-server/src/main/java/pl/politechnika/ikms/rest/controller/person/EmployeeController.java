package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeDto;
import pl.politechnika.ikms.rest.dto.person.EmployeeGeneralDetailDto;
import pl.politechnika.ikms.rest.dto.person.PhoneNumberDto;
import pl.politechnika.ikms.rest.mapper.person.EmployeeEntityMapper;
import pl.politechnika.ikms.service.person.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final @NonNull EmployeeService employeeService;

    @GetMapping(value = "/{employeeId}")
    @ResponseBody
    public EmployeeDto getOneEmployee(@PathVariable Long employeeId){
        return employeeService.findOne(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        return employeeService.create(employeeDto);
    }

    @GetMapping
    public Page<EmployeeDto> getAllEmployees(Pageable pageable){
       return employeeService.findAllPaginated(pageable);
    }

    @PutMapping
    public EmployeeDto updateEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        return employeeService.update(employeeDto);
    }

    @DeleteMapping(value = "/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId){
        employeeService.deleteById(employeeId);
    }

    @GetMapping(value = "/general")
    public Page<EmployeeGeneralDetailDto> getEmployeeGeneralDetails(Pageable pageable){
        return employeeService.getEmployeesGeneralDetails(pageable);
    }

    @GetMapping(value = "/minimal")
    public List<MinimalDto<Long, String>> getEmployeesMinimal(){
        return employeeService.getEmployeesMinimal();
    }

    @GetMapping(value = "/phoneNumbers")
    @Transactional
    public List<PhoneNumberDto> getEmployeesPhoneNumbers(){
        return employeeService.getEmployeesPhoneNumbers();
    }

}
