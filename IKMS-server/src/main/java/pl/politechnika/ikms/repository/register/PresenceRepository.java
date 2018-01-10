package pl.politechnika.ikms.repository.register;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.register.PresenceEntity;

@Repository
public interface PresenceRepository extends JpaRepository<PresenceEntity, Long> {
}
