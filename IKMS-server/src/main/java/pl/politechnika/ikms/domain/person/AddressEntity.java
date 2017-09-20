package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "addresses")
@ToString(exclude = "user")
@SequenceGenerator(name="addresses_seq_name",sequenceName="addresses_seq", allocationSize = 1)
public class AddressEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_seq_name")
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id")
    private PersonalDataEntity personalData;

    @Size(max = 35)
    @Column(name = "street")
    private String street;

    @Min(1)
    @Column(name = "street_number")
    private Integer streetNumber;

    @Column(name = "house_number")
    private String houseNumber;

    @Size(max = 6)
    @Column(name = "post_code")
    private String postCode;

    @Size(max = 35)
    @Column(name = "city")
    private String city;

    @Size(max = 35)
    @Column(name = "community")
    private String community;

    @Size(max = 35)
    @Column(name = "county")
    private String county;

    @Size(max = 35)
    @Column(name = "voivodeship")
    private String voivodeship;

    @Size(max = 35)
    @Column(name = "country")
    private String country;
}
