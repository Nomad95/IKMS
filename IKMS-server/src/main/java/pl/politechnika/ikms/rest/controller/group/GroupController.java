package pl.politechnika.ikms.rest.controller.group;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.group.GroupDto;
import pl.politechnika.ikms.rest.mapper.group.GroupEntityMapper;
import pl.politechnika.ikms.service.group.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final @NonNull GroupService groupService;
    private final @NonNull GroupEntityMapper groupEntityMapper;

    @GetMapping(value = "/{groupId}")
    public GroupDto getOneGroup(@PathVariable Long groupId){
        return groupEntityMapper.convertToDto(groupService.findOne(groupId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto createGroup(@Valid @RequestBody GroupDto groupDto){
        GroupEntity groupEntity = groupService.create(groupEntityMapper.convertToEntity(groupDto));
        return groupEntityMapper.convertToDto(groupEntity);
    }

    @GetMapping
    public Page<GroupDto> getAllGroups(Pageable pageable){
        return groupService.findAllPaginated(pageable).map(groupEntityMapper::convertToDto);
    }

    @PutMapping
    public GroupDto updateGroup(@Valid @RequestBody GroupDto groupDto){
        GroupEntity groupEntity = groupService.update(groupEntityMapper.convertToEntity(groupDto));
        return groupEntityMapper.convertToDto(groupEntity);
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
