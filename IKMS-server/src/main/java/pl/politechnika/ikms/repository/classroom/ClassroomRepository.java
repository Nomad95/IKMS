package pl.politechnika.ikms.repository.classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(c.id, c.name)"
            + " FROM ClassroomEntity c")
    List<MinimalDto<Long,String>> getAllMinimal();
}
