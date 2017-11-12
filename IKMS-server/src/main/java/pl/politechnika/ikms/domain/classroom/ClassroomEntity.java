package pl.politechnika.ikms.domain.classroom;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "classrooms")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name="classrooms_seq_name",sequenceName="classrooms_seq", allocationSize = 1)
public class ClassroomEntity extends AbstractEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classrooms_seq_name")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "classroom")
    private List<ScheduleActivityEntity> scheduleActivities;
}

