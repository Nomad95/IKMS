package pl.politechnika.ikms.service.classroom;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.classroom.ClassroomDto;

import java.util.List;

public interface ClassroomService extends GenericService<ClassroomDto> {
    List<MinimalDto<Long,String>> getAllMinimal();
}
