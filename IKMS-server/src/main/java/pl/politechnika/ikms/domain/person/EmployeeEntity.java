package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true, exclude = "personalData")
@ToString(exclude = "groupEntity")
@SequenceGenerator(name="employees_seq_name",sequenceName="employees_seq", allocationSize = 1)
public class EmployeeEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id", nullable = false)
    private PersonalDataEntity personalData;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role", nullable = false)
    private EmployeeRole employeeRole;

    @Column(name = "nip", length = 13)
    private String nip;

    @OneToOne(mappedBy = "employee")
    private GroupEntity groupEntity;
}
