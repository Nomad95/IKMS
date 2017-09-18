package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.domain.person.enums.AddressType;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDto extends AbstractDto {

    private Long id;

    @NotNull
    private AddressType addressType;

    private MinimalDto<Long,String> user;

    @Size(max = 35)
    private String street;

    @Min(1)
    private Integer streetNumber;

    @Min(1)
    private String houseNumber;

    @Size(max = 6)
    private String postCode;

    @Size(max = 35)
    private String city;

    @Size(max = 35)
    private String community;

    @Size(max = 35)
    private String county;

    @Size(max = 35)
    private String voivodeship;

    @Size(max = 35)
    private String country;
}
