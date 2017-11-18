package pl.politechnika.ikms.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {

    @Query("SELECT NEW pl.politechnika.ikms.rest.dto.MinimalDto(g.id, g.name) "
            + " FROM GroupEntity g")
    List<MinimalDto<Long,String>> getGroupsMinimal();
}
