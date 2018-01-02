package pl.politechnika.ikms.repository.upload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.upload.DidacticMaterialFileEntity;

import java.util.List;

@Repository
public interface DidacticMaterialFileRepository extends JpaRepository<DidacticMaterialFileEntity, Long>{

    @Query("SELECT dm FROM DidacticMaterialFileEntity dm " +
            "JOIN dm.sharedEmployees se " +
            "WHERE :employeeId in (se.id)")
    List<DidacticMaterialFileEntity> getAllForEmployee(@Param("employeeId")Long employeeId);

    @Query("SELECT dm FROM DidacticMaterialFileEntity dm " +
            "JOIN dm.sharedParents sp " +
            "WHERE :parentId in (sp.id)")
    List<DidacticMaterialFileEntity> getAllForParent(@Param("parentId")Long parentId);

   //todo: get files for group and associated parents
}
