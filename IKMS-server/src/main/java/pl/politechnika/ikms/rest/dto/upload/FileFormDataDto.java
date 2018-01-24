package pl.politechnika.ikms.rest.dto.upload;

import lombok.Data;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FileFormDataDto extends AbstractDto {

    @Size(max = 255)
    private String description;

    @NotNull
    @Size(max = 50)
    private String folder;

    @NotNull
    @Size(max = 50)
    private String subfolder;

    private String selectedGroups;

    private String selectedEmployees;

    private String selectedParents;

    @Override
    public Long getId() {
        return null;
    }
}
