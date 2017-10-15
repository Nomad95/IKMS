package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.AddressType;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = "personalData")
@Table(name = "addresses")
@ToString(exclude = "personalData")
@SequenceGenerator(name="addresses_seq_name",sequenceName="addresses_seq", allocationSize = 1)
public class AddressEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addresses_seq_name")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_data_id", nullable = false)
    private PersonalDataEntity personalData;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false)
    private AddressType addressType;

    @Column(name = "street", length = 35)
    private String street;

    @Column(name = "street_number")
    private Integer streetNumber;

    @Column(name = "house_number", length = 10)
    private String houseNumber;

    @Column(name = "post_code", length = 6)
    private String postCode;

    @Column(name = "city", length = 35)
    private String city;

    @Column(name = "community", length = 35)
    private String community;

    @Column(name = "county", length = 35)
    private String county;

    @Column(name = "voivodeship", length = 35)
    private String voivodeship;

    @Column(name = "country", length = 35)
    private String country;
}
