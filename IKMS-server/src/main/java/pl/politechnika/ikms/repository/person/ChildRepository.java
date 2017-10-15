package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.ChildEntity;

@Repository
public interface ChildRepository extends JpaRepository<ChildEntity, Long> {
}
