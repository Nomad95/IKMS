package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.Gender;
import pl.politechnika.ikms.domain.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "parents")
@SequenceGenerator(name="parents_seq_name",sequenceName="parents_seq", allocationSize = 1)
public class ParentEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parents_seq_name")
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @NotEmpty
    @Size(max = 25)
    @Column(name = "name")
    private String name;

    @Size(max = 25)
    @Column(name = "secondary_name")
    private String secondaryName;

    @NotEmpty
    @Size(max = 35)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @DecimalMax(value = "99999999999")
    @Column(name = "pesel")
    private BigInteger pesel;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Size(max = 35)
    @Column(name = "family_name")
    private String familyName;

    @Size(max = 25)
    @Column(name = "mothers_maiden_name")
    private String mothersMaidenName;

    @Size(max = 25)
    @Column(name = "fathers_name")
    private String fathersName;

    @Size(max = 25)
    @Column(name = "mothers_name")
    private String mothersName;

    @NotNull
    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @NotEmpty
    @Size(max = 45)
    @Column(name = "nationality")
    private String nationality;

    @DecimalMax(value = "999999999")
    @Column(name = "contact_number")
    private BigInteger contactNumber;

    @DecimalMax(value = "999999999")
    @Column(name = "fax_number")
    private BigInteger faxNumber;

}
