package pl.politechnika.ikms.rest.dto.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneNumberDto extends AbstractDto{

    private Long userId;

    private String fullName;

    private String phoneNumber;
}
