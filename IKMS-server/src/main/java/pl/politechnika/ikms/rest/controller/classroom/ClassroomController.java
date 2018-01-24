package pl.politechnika.ikms.rest.controller.classroom;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.classroom.ClassroomDto;
import pl.politechnika.ikms.service.classroom.ClassroomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final @NonNull ClassroomService classroomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClassroomDto createClassroom(@Valid @RequestBody ClassroomDto classroomDto){
        return classroomService.create(classroomDto);
    }

    @GetMapping
    public Page<ClassroomDto> getAllClassrooms(Pageable pageable){
        return classroomService.findAllPaginated(pageable);
    }

    @GetMapping(value = "/{classroomId}")
    public ClassroomDto getOneClassroom(@PathVariable Long classroomId){
        return classroomService.findOne(classroomId);
    }

    @PutMapping
    public ClassroomDto updateClassroom(@Valid @RequestBody ClassroomDto classroomDto){
        return classroomService.update(classroomDto);
    }

    @DeleteMapping(value = "/{classroomId}")
    public void deleteClassroom(@PathVariable Long classroomId){
        classroomService.deleteById(classroomId);
    }

    @GetMapping(value = "/minimal/all")
    public List<MinimalDto<Long, String>> getAllClassroomsMinimal(){
        return classroomService.getAllMinimal();
    }

}
