package pl.politechnika.ikms.rest.mapper.upload;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;
import pl.politechnika.ikms.repository.group.GroupRepository;
import pl.politechnika.ikms.repository.person.EmployeeRepository;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.upload.DidacticMaterialFileDto;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DidacticMaterialFileMapper extends AbstractModelMapper<DidacticMaterialFileEntity, DidacticMaterialFileDto> {

    private final @NonNull ParentRepository parentRepository;
    private final @NonNull EmployeeRepository employeeRepository;
    private final @NonNull GroupRepository groupRepository;

    @Autowired
    public DidacticMaterialFileMapper(ModelMapper modelMapper, ParentRepository parentRepository,
        EmployeeRepository employeeRepository, GroupRepository groupRepository) {
        super(modelMapper);
        this.parentRepository = parentRepository;
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public DidacticMaterialFileDto convertToDto(DidacticMaterialFileEntity entity) {
        DidacticMaterialFileDto dto = modelMapper.map(entity, DidacticMaterialFileDto.class);
        if (Objects.nonNull(entity.getSharedEmployees())) {
            dto.setSharedEmployees(entity.getSharedEmployees().stream()
                    .map(employee -> new MinimalDto<>(
                            employee.getId(),
                            employee.getPersonalData().getName() + " " + employee.getPersonalData().getSurname()))
                    .collect(Collectors.toList()));
        }
        if (Objects.nonNull(entity.getSharedGroups())) {
            dto.setSharedGroups(entity.getSharedGroups().stream()
                    .map(group -> new MinimalDto<>(
                            group.getId(),
                            group.getName()))
                    .collect(Collectors.toList()));
        }
        if (Objects.nonNull(entity.getSharedParents())) {
            dto.setSharedParents(entity.getSharedParents().stream()
                    .map(parent -> new MinimalDto<>(
                            parent.getId(),
                            parent.getPersonalData().getName() + " " + parent.getPersonalData().getSurname()))
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public DidacticMaterialFileEntity convertToEntity(DidacticMaterialFileDto dto) {
        DidacticMaterialFileEntity entity = modelMapper.map(dto, DidacticMaterialFileEntity.class);
        if (Objects.nonNull(dto.getSharedEmployees())) {
            entity.setSharedEmployees(employeeRepository.findAll(dto.getSharedEmployees().stream()
                    .map(MinimalDto::getId).collect(Collectors.toList())));
        }
        if (Objects.nonNull(dto.getSharedGroups())) {
            entity.setSharedGroups(groupRepository.findAll(dto.getSharedGroups().stream()
                    .map(MinimalDto::getId).collect(Collectors.toList())));
        }
        if (Objects.nonNull(dto.getSharedParents())) {
            entity.setSharedParents(parentRepository.findAll(dto.getSharedParents().stream()
                    .map(MinimalDto::getId).collect(Collectors.toList())));
        }

        return entity;
    }

    public DidacticMaterialFileDto convertToDtoLazyContent(DidacticMaterialFileEntity entity) {
        DidacticMaterialFileDto dto = new DidacticMaterialFileDto();
        dto.setId(entity.getId());
        dto.setFolder(entity.getFolder());
        dto.setSubfolder(entity.getSubfolder());
        dto.setFilename(entity.getFilename());
        dto.setDateOfUpload(entity.getDateOfUpload());
        dto.setDescription(entity.getDescription());
        dto.setExtension(entity.getExtension());
        dto.setIcon(entity.getIcon());
        dto.setSize(entity.getSize());

        if (Objects.nonNull(entity.getSharedEmployees())) {
            dto.setSharedEmployees(entity.getSharedEmployees().stream()
                    .map(employee -> new MinimalDto<>(
                            employee.getId(),
                            employee.getPersonalData().getName() + " " + employee.getPersonalData().getSurname()))
                    .collect(Collectors.toList()));
        }
        if (Objects.nonNull(entity.getSharedGroups())) {
            dto.setSharedGroups(entity.getSharedGroups().stream()
                    .map(group -> new MinimalDto<>(
                            group.getId(),
                            group.getName()))
                    .collect(Collectors.toList()));
        }
        if (Objects.nonNull(entity.getSharedParents())) {
            dto.setSharedParents(entity.getSharedParents().stream()
                    .map(parent -> new MinimalDto<>(
                            parent.getId(),
                            parent.getPersonalData().getName() + " " + parent.getPersonalData().getSurname()))
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
