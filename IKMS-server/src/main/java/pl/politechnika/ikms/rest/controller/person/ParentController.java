package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentDto;
import pl.politechnika.ikms.rest.dto.person.ParentGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ParentEntityMapper;
import pl.politechnika.ikms.service.person.ParentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final @NonNull ParentService parentService;
    private final @NonNull ParentEntityMapper parentEntityMapper;

    @GetMapping(value = "/{parentId}")
    public ParentDto getOneParent(@PathVariable Long parentId){
        return parentEntityMapper.convertToDto(parentService.findOne(parentId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParentDto createParent(@Valid @RequestBody ParentDto parentDto){
        ParentEntity parentEntity = parentService.create(parentEntityMapper.convertToEntity(parentDto));
        return parentEntityMapper.convertToDto(parentEntity);
    }

    @GetMapping
    public Page<ParentDto> getAllParents(Pageable pageable){
        return parentService.findAllPaginated(pageable).map(parentEntityMapper::convertToDto);
    }

    @PutMapping
    public ParentDto updateParent(@Valid @RequestBody ParentDto parentDto){
        ParentEntity parentEntity = parentService.update(parentEntityMapper.convertToEntity(parentDto));
        return parentEntityMapper.convertToDto(parentEntity);
    }

    @DeleteMapping(value = "/{parentId}")
    public void deleteParent(@PathVariable Long parentId){
        parentService.deleteById(parentId);
    }

    @GetMapping(value = "/general")
    public Page<ParentGeneralDetailDto> getParentGeneralDetails(Pageable pageable){
        return parentService.getParentGeneralDetails(pageable);
    }

    @GetMapping(value = "/minimal/all")
    public List<MinimalDto<Long, String>> getAllParentsMinimal(){
        return parentService.getAllMinimal();
    }
}
