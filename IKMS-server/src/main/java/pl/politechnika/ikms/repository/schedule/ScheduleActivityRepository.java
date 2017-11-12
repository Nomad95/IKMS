package pl.politechnika.ikms.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;

@Repository
public interface ScheduleActivityRepository extends JpaRepository<ScheduleActivityEntity, Long> {
}
