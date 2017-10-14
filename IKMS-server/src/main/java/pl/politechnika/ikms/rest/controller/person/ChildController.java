package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.service.person.ChildService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/child")
@RequiredArgsConstructor
public class ChildController {

    private final @NonNull ChildService childService;
    private final @NonNull ChildEntityMapper childEntityMapper;

    @GetMapping(value = "/{childId}")
    @ResponseBody
    public ChildDto getOneChild(@PathVariable Long childId){
        return childEntityMapper.convertToDto(childService.findOne(childId));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ChildDto createChild(@Valid @RequestBody ChildDto ChildDto){
        ChildEntity ChildEntity = childService.create(childEntityMapper.convertToEntity(ChildDto));
        return childEntityMapper.convertToDto(ChildEntity);
    }

    @GetMapping
    @ResponseBody
    public Page<ChildDto> getAllChildes(Pageable pageable){
        return childService.findAllPaginated(pageable).map(childEntityMapper::convertToDto);
    }

    @PutMapping
    @ResponseBody
    public ChildDto updateChild(@Valid @RequestBody ChildDto ChildDto){
        ChildEntity ChildEntity = childService.update(childEntityMapper.convertToEntity(ChildDto));
        return childEntityMapper.convertToDto(ChildEntity);
    }

    @DeleteMapping(value = "/{childId}")
    public void deleteChild(@PathVariable Long childId){
        childService.deleteById(childId);
    }
}
