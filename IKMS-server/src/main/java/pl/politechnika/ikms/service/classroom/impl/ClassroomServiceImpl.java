package pl.politechnika.ikms.service.classroom.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.repository.classroom.ClassroomRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.classroom.ClassroomDto;
import pl.politechnika.ikms.rest.mapper.classroom.ClassroomEntityMapper;
import pl.politechnika.ikms.service.classroom.ClassroomService;

import java.util.List;

@Service
public class ClassroomServiceImpl extends AbstractService<ClassroomEntity, ClassroomDto, ClassroomRepository, ClassroomEntityMapper> implements ClassroomService {

    public ClassroomServiceImpl(ClassroomRepository repository, ClassroomEntityMapper classroomEntityMapper) {
        super(repository, classroomEntityMapper, ClassroomEntity.class);
    }

    @Override
    public List<MinimalDto<Long, String>> getAllMinimal() {
        return getRepository().getAllMinimal();
    }
}
