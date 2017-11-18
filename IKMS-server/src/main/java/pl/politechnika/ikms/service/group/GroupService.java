package pl.politechnika.ikms.service.group;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.util.List;

public interface GroupService extends GenericService<GroupEntity>{
    List<MinimalDto<Long,String>> getGroupsMinimal();
}
