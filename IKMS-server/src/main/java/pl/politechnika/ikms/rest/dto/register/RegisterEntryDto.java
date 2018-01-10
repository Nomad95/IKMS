package pl.politechnika.ikms.rest.dto.register;

import lombok.Data;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.time.LocalDateTime;

@Data
public class RegisterEntryDto {

    private MinimalDto<Long, String> child;

    private String description;

    private LocalDateTime created;

    private MinimalDto<Long, String>  employee;

    private Long registerId;
}
