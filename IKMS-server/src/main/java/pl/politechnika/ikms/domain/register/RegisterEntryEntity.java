package pl.politechnika.ikms.domain.register;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Entity
@Data
@Table(name = "register_entry")
@EqualsAndHashCode(callSuper = true, exclude = "id, child, employee")
@ToString(exclude = {"child", "employee"})
@SequenceGenerator(name="register_entry_seq_name",sequenceName="register_entry_seq", allocationSize = 1)
public class RegisterEntryEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_entry_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private ChildEntity child;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "register_id")
    private RegisterEntity register;

}
