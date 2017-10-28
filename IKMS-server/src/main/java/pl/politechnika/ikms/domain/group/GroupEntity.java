package pl.politechnika.ikms.domain.group;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(mappedBy = "group")
    private List<ChildEntity> children;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
