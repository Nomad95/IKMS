package pl.politechnika.ikms.service.group;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.group.GroupDto;

import java.util.List;

public interface GroupService extends GenericService<GroupDto>{
    List<MinimalDto<Long,String>> getGroupsMinimal();
}
