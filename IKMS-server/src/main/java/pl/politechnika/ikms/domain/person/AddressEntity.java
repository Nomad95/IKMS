package pl.politechnika.ikms.domain.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.politechnika.ikms.commons.abstracts.AbstractEntity;
import pl.politechnika.ikms.domain.person.enums.AddressType;
import pl.politechnika.ikms.domain.user.UserEntity;

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
    @Column(name = "address_type")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

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
