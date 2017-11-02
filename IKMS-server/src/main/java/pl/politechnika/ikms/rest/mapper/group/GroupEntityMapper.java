package pl.politechnika.ikms.rest.mapper.group;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.group.GroupEntity;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.group.GroupDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class    GroupEntityMapper extends AbstractModelMapper<GroupEntity, GroupDto> {

    private final @NonNull EmployeeRepository employeeRepository;
    private final @NonNull ChildRepository childRepository;

    public GroupEntityMapper(ModelMapper modelMapper, EmployeeRepository employeeRepository, ChildRepository childRepository) {
        super(modelMapper);
        this.employeeRepository = employeeRepository;
        this.childRepository = childRepository;
    }

    @Override
    public GroupDto convertToDto(GroupEntity groupEntity) {
        GroupDto groupDto = modelMapper.map(groupEntity, GroupDto.class);

        if(Objects.nonNull(groupEntity.getEmployee())) {
            groupDto.setEmployee(new MinimalDto<>(groupEntity.getEmployee().getId(),
                    groupEntity.getEmployee().getPersonalData().getName()
                            + " " + groupEntity.getEmployee().getPersonalData().getSurname()));
        }

        if(Objects.nonNull(groupEntity.getChildren()))
            groupDto.setGroupSize(groupEntity.getChildren().size());

        if(Objects.nonNull(groupEntity.getChildren())) {
            ArrayList<MinimalDto<Long, String>> children = groupEntity.getChildren().stream()
                    .map(c -> new MinimalDto<>(
                            c.getId(),
                            c.getPersonalData().getName() + " " + c.getPersonalData().getSurname()))
                    .collect(Collectors.toCollection(ArrayList::new));
            groupDto.setChildren(children);
        }

        return groupDto;
    }

    @Override
    public GroupEntity convertToEntity(GroupDto groupDto) {
        GroupEntity groupEntity = modelMapper.map(groupDto, GroupEntity.class);

        if(Objects.nonNull(groupDto.getEmployee()))
            groupEntity.setEmployee(employeeRepository.getOne(groupDto.getEmployee().getId()));

        if(Objects.nonNull(groupDto.getChildren())){
            ArrayList<Long> ids = groupDto.getChildren().stream()
                    .map(MinimalDto::getId).collect(Collectors.toCollection(ArrayList::new));
            List<ChildEntity> children = childRepository.findAll(ids);
            children.forEach(c -> c.setGroup(groupEntity));
            groupEntity.setChildren(children);
        }

        return groupEntity;
    }
}
