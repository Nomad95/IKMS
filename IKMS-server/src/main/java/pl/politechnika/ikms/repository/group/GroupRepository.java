package pl.politechnika.ikms.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.group.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {
}
