package pl.politechnika.ikms.service.classroom.impl;

import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.repository.classroom.ClassroomRepository;
import pl.politechnika.ikms.service.classroom.ClassroomService;

@Service
public class ClassroomServiceImpl extends AbstractService<ClassroomEntity, ClassroomRepository> implements ClassroomService {

    public ClassroomServiceImpl(ClassroomRepository repository) {
        super(repository, ClassroomEntity.class);
    }
}
