package pl.politechnika.ikms.rest.controller.classroom;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.classroom.ClassroomEntity;
import pl.politechnika.ikms.rest.dto.classroom.ClassroomDto;
import pl.politechnika.ikms.rest.mapper.classroom.ClassroomEntityMapper;
import pl.politechnika.ikms.service.classroom.ClassroomService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final @NonNull ClassroomService classroomService;
    private final @NonNull ClassroomEntityMapper classroomEntityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClassroomDto createClassroom(@Valid @RequestBody ClassroomDto classroomDto){
        ClassroomEntity classroomEntity = classroomService.create(classroomEntityMapper.convertToEntity(classroomDto));
        return classroomEntityMapper.convertToDto(classroomEntity);
    }

    @GetMapping
    public Page<ClassroomDto> getAllClassrooms(Pageable pageable){
        return classroomService.findAllPaginated(pageable).map(classroomEntityMapper::convertToDto);
    }

    @GetMapping(value = "/{classroomId}")
    public ClassroomDto getOneClassroom(@PathVariable Long classroomId){
        return classroomEntityMapper.convertToDto(classroomService.findOne(classroomId));
    }

    @PutMapping
    public ClassroomDto updateClassroom(@Valid @RequestBody ClassroomDto classroomDto){
        ClassroomEntity classroomEntity = classroomService.update(classroomEntityMapper.convertToEntity(classroomDto));
        return classroomEntityMapper.convertToDto(classroomEntity);
    }

    @DeleteMapping(value = "/{classroomId}")
    public void deleteClassroom(@PathVariable Long classroomId){
        classroomService.deleteById(classroomId);
    }

}
