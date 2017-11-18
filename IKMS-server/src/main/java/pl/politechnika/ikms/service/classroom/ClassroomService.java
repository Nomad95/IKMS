package pl.politechnika.ikms.service.classroom;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

public interface ClassroomService extends GenericService<ClassroomEntity> {
    List<MinimalDto<Long,String>> getAllMinimal();
}
