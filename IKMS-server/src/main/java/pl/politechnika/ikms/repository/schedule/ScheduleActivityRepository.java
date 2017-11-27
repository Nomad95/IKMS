package pl.politechnika.ikms.repository.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleActivityRepository extends JpaRepository<ScheduleActivityEntity, Long> {

    @Query("select count(sa) from ScheduleActivityEntity sa " +
            "where sa.activityStart < :activityEnd " +
            "and sa.activityEnd > :activityStart " +
            "and sa.classroom.id = :classroomId " +
            "and sa.id <> :activityId")
    int checkClassroomOccupancy(
            @Param("activityEnd")LocalDateTime activityEnd,
            @Param("activityStart")LocalDateTime activityStart,
            @Param("classroomId")Long classroomId,
            @Param("activityId")Long activityId);

    @Query("select count(sa) from ScheduleActivityEntity sa " +
            "where sa.activityStart < :activityEnd " +
            "and sa.activityEnd > :activityStart " +
            "and sa.group.id = :groupId " +
            "and sa.id <> :activityId")
    int checkIfGroupIsBusy(
            @Param("activityEnd")LocalDateTime activityEnd,
            @Param("activityStart")LocalDateTime activityStart,
            @Param("groupId")Long groupId,
            @Param("activityId")Long activityId);

    @Query("select count(sa) from ScheduleActivityEntity sa " +
            "join sa.employees e " +
            "where sa.activityStart < :activityEnd " +
            "and sa.activityEnd > :activityStart " +
            "and e.id = :employeeId " +
            "and sa.id <> :activityId")
    int checkIfEmployeeIsBusy(
            @Param("activityEnd")LocalDateTime activityEnd,
            @Param("activityStart")LocalDateTime activityStart,
            @Param("employeeId")Long employeeId,
            @Param("activityId")Long activityId);

    @Query("select count(sa) from ScheduleActivityEntity sa " +
            "join sa.children c " +
            "where sa.activityStart < :activityEnd " +
            "and sa.activityEnd > :activityStart " +
            "and c.id = :childrenId " +
            "and sa.id <> :activityId")
    int checkIfChildIsBusy(
            @Param("activityEnd")LocalDateTime activityEnd,
            @Param("activityStart")LocalDateTime activityStart,
            @Param("childrenId")Long childrenId,
            @Param("activityId")Long activityId);


    @Query("select sa from ScheduleActivityEntity sa " +
            "join sa.employees em " +
            "where em.id = :employeeId")
    List<ScheduleActivityEntity> findAllForEmployee(@Param("employeeId") Long employeeId);

    @Query("select sa from ScheduleActivityEntity sa " +
            "join sa.employees em " +
            "where em.id = :employeeId")
    List<ScheduleActivityEntity> findAllForLoggedEmployee(@Param("employeeId") Long employeeId);
}
