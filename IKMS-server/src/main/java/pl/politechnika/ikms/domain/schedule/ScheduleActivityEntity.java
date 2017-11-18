package pl.politechnika.ikms.domain.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "schedule_activities")
@ToString(exclude = {"errors","employees","children"})
@EqualsAndHashCode(callSuper = true, exclude = {"errors","employees","children"})
@SequenceGenerator(name="schedule_activities_seq_name",sequenceName="schedule_activities_seq", allocationSize = 1)
public class ScheduleActivityEntity extends AbstractEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_activities_seq_name")
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "activity_start")
    private LocalDateTime activityStart;

    @Column(name = "activity_end")
    private LocalDateTime activityEnd;

    @Column(name = "comment")
    private String comment;

    @OneToMany
    @JoinColumn(name="activity_id")
    private List<ScheduleActivityError> errors;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "classroom_id")
    private ClassroomEntity classroom;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "activities_has_employees",
            joinColumns = {@JoinColumn(name = "schedule_activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<EmployeeEntity> employees;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "activities_has_children",
            joinColumns = {@JoinColumn(name = "schedule_activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")})
    private List<ChildEntity> children;
}
