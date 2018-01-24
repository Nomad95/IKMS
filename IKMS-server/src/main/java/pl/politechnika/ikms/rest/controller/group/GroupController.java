package pl.politechnika.ikms.rest.controller.group;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.group.GroupDto;
import pl.politechnika.ikms.service.group.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final @NonNull GroupService groupService;

    @GetMapping(value = "/{groupId}")
    public GroupDto getOneGroup(@PathVariable Long groupId){
        return groupService.findOne(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto createGroup(@Valid @RequestBody GroupDto groupDto){
        return groupService.create(groupDto);
    }

    @GetMapping
    public Page<GroupDto> getAllGroups(Pageable pageable){
        return groupService.findAllPaginated(pageable);
    }

    @PutMapping
    public GroupDto updateGroup(@Valid @RequestBody GroupDto groupDto){
        return groupService.update(groupDto);
    }

    @DeleteMapping(value = "/{groupId}")
    public void deleteGroup(@PathVariable Long groupId){
        groupService.deleteById(groupId);
    }

    @GetMapping(value = "/minimal/all")
    public List<MinimalDto<Long, String>> getGroupsMinimal(){
        return groupService.getGroupsMinimal();
    }
}
