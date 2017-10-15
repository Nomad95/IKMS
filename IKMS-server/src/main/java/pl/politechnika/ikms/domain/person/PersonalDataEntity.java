package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.Gender;
import pl.politechnika.ikms.domain.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"user","addresses"})
@Table(name = "personal_data")
@ToString(exclude = {"user","addresses"})
@SequenceGenerator(name="personal_data_seq_name",sequenceName="personal_data_seq", allocationSize = 1)
public class PersonalDataEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_data_seq_name")
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "secondary_name", length = 25)
    private String secondaryName;

    @Column(name = "surname", length = 35, nullable = false)
    private String surname;

    @Column(name = "pesel", length = 11, nullable = false)
    private String pesel;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "place_of_birth", length = 30, nullable = false)
    private String placeOfBirth;

    @Column(name = "family_name", length = 35)
    private String familyName;

    @Column(name = "mothers_maiden_name", length = 35)
    private String mothersMaidenName;

    @Column(name = "fathers_name", length = 25)
    private String fathersName;

    @Column(name = "mothers_name", length = 25)
    private String mothersName;

    @Column(name = "gender", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality", length = 45, nullable = false)
    private String nationality;

    @Column(name = "contact_number", length = 12)
    private String contactNumber;

    @Column(name = "fax_number", length = 12)
    private String faxNumber;

    @OneToMany(mappedBy = "personalData",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();
}
