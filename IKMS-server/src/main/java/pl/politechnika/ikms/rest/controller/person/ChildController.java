package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.service.person.ChildService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/child")
@RequiredArgsConstructor
public class ChildController {

    private final @NonNull ChildService childService;

    @GetMapping(value = "/{childId}")
    public ChildDto getOneChild(@PathVariable Long childId){
        return childService.findOne(childId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChildDto createChild(@Valid @RequestBody ChildDto childDto){
        return childService.create(childDto);
    }

    @GetMapping
    public Page<ChildDto> getAllChildes(Pageable pageable){
        return childService.findAllPaginated(pageable);
    }

    @PutMapping
    public ChildDto updateChild(@Valid @RequestBody ChildDto childDto){
        return childService.update(childDto);
    }

    @DeleteMapping(value = "/{childId}")
    public void deleteChild(@PathVariable Long childId){
        childService.deleteById(childId);
    }

    @GetMapping(value = "/general")
    public Page<ChildGeneralDetailDto> getChildrenGeneralDetails(Pageable pageable){
        return childService.getChildGeneralDetail(pageable);
    }

    @PostMapping(value = "/general")
    public List<ChildGeneralDetailDto> getChildrenGeneralDetailsByIds(@RequestBody List<Long> childrenIds){
        return childService.getGeneralDto(childrenIds);
    }

    @GetMapping(value = "/minimal/groupless")
    public List<MinimalDto<Long, String>> getGrouplessChildrenMinimal(){
        return childService.getGrouplessChildrenMinimal();
    }

    @GetMapping(value = "/minimal/all")
    public List<MinimalDto<Long, String>> getChildrenMinimal(){
        return childService.getChildrenMinimal();
    }
}
