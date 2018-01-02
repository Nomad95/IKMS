package pl.politechnika.ikms.domain.group;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityEntity;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"employee","children","scheduleActivities","sharedFiles"})
@SequenceGenerator(name="gropus_seq_name",sequenceName="groups_seq", allocationSize = 1)
public class GroupEntity extends AbstractEntity{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gropus_seq_name")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<ChildEntity> children = Lists.newArrayList();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<ScheduleActivityEntity> scheduleActivities;

    @ManyToMany(mappedBy = "sharedGroups")
    private List<DidacticMaterialFileEntity> sharedFiles;
}
