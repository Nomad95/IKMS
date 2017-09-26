package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.EmployeeRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "employees")
@EqualsAndHashCode(callSuper = true, exclude = "personalData")
@ToString(exclude = "personalData")
@SequenceGenerator(name="employees_seq_name",sequenceName="employees_seq", allocationSize = 1)
public class EmployeeEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_seq_name")
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id")
    private PersonalDataEntity personalData;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole;

    @Size(max = 13)
    @Column(name = "nip")
    private String nip;
}
