package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<ParentEntity,Long> {

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(p.id, concat( p.personalData.name, ' ', p.personalData.surname)) FROM ParentEntity p")
    List<MinimalDto<Long, String>> getAllMinimalDto();
}
