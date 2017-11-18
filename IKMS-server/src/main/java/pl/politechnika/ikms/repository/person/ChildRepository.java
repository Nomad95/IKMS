package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    @Query("SELECT c FROM ChildEntity c WHERE c.id IN :ids ")
    List<ChildEntity> getGeneralDto(@Param("ids") List<Long> childrenIds);

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(c.id, concat(c.personalData.name, ' ', c.personalData.surname) ) "
            + " FROM ChildEntity c WHERE c.group is NULL ")
    List<MinimalDto<Long, String>> getGrouplessChildrenMinimal();

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(c.id, concat(c.personalData.name, ' ', c.personalData.surname) ) "
            + " FROM ChildEntity c")
    List<MinimalDto<Long,String>> getChildrenMinimal();
}
