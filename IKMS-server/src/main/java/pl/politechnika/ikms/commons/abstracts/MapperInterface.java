package pl.politechnika.ikms.commons.abstracts;

public interface MapperInterface<ENTITY, DTO> {
    DTO convertToDto(ENTITY entity);
    ENTITY convertToEntity(DTO dto);
}
