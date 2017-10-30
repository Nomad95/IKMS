package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(e.id, concat(e.personalData.name, ' ',e.personalData.surname)) "
            + " FROM EmployeeEntity e")
    List<MinimalDto<Long, String>> getEmployeesMinimal();
}
