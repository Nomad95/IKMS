package pl.politechnika.ikms.rest.dto.upload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.politechnika.ikms.commons.abstracts.AbstractDto;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class DidacticMaterialFileDto extends AbstractDto {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String filename;

    @NotNull
    @Size(max = 50)
    private String folder;

    @NotNull
    @Size(max = 50)
    private String subfolder;

    @NotNull
    @Size(max = 20)
    private String size;

    @Size(max = 5)
    private String extension;

    @Size(max = 20)
    private String icon;

    private String description;

    private byte[] content;

    private LocalDate dateOfUpload;

    private List<MinimalDto<Long, String>> sharedGroups;

    private List<MinimalDto<Long, String>> sharedParents;

    private List<MinimalDto<Long, String>> sharedEmployees;
}
