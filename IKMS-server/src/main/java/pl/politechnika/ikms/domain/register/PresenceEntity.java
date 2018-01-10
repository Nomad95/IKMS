package pl.politechnika.ikms.domain.register;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.domain.person.EmployeeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "presence")
@EqualsAndHashCode(callSuper = true, exclude = {"child", "checkingPerson"})
@ToString(exclude = {"child", "checkingPerson"})
@SequenceGenerator(name="presence_seq_name",sequenceName="presence_seq", allocationSize = 1)
public class PresenceEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presence_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id") // nl
    private ChildEntity child;

    @Column(name = "was_present")
    private boolean wasPresent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checking_person_id") // nl
    private EmployeeEntity checkingPerson;

    @Column(name = "checking_time") // nl
    private LocalDateTime checkingTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "register_id")
    private RegisterEntity register;
}
