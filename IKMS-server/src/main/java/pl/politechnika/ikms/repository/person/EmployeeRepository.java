package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(e.id, concat(e.personalData.name, ' ',e.personalData.surname)) "
            + " FROM EmployeeEntity e")
    List<MinimalDto<Long, String>> getEmployeesMinimal();

    @Query("select new pl.politechnika.ikms.rest.dto.MinimalDto(e.id, concat(e.personalData.name, ' ',e.personalData.surname)) " +
            "from EmployeeEntity e " +
            "join e.personalData pd " +
            "join pd.user u " +
            "where u.id = :userId")
    MinimalDto<Long, String> getEmployeeMinimalByUserId(@Param("userId") Long userId);

    @Query("select e.id " +
            "from EmployeeEntity e " +
            "join e.personalData pd " +
            "join pd.user u " +
            "where u.id = :userId")
    Long getEmployeeIdByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM EmployeeEntity e " +
            "JOIN e.personalData pd " +
            "WHERE pd.user.id = :userId")
    Optional<EmployeeEntity> getByUserId(@Param("userId") Long userId);
}
