package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.DisabilityLevel;

import javax.persistence.*;

@Data
@Entity
@Table(name = "children")
@EqualsAndHashCode(callSuper = true, exclude = {"personalData","parent"})
@ToString(exclude = {"personalData","parent"})
@SequenceGenerator(name="children_seq_name",sequenceName="children_seq", allocationSize = 1)
public class ChildEntity extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "children_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id", nullable = false)
    private PersonalDataEntity personalData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ParentEntity parent;

    @Column(name ="diseases", length = 300)
    private String diseases; //todo: consider in another place

    @Column(name = "allergies", length = 300)
    private String allergies; //todo: consider in another place

    @Column(name = "disability_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private DisabilityLevel disabilityLevel;
}
