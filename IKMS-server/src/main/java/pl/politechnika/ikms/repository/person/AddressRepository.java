package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.AddressEntity;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

    List<AddressEntity> findByPersonalDataId(Long id);

    @Query(nativeQuery = true, value = "select a.* from public.personal_data pd " +
            "join public.parents p on pd.id = p.personal_data_id " +
            "join public.addresses a on pd.id = a.personal_data_id " +
            "where p.id = :parentId")
    List<AddressEntity> findByParentId(@Param("parentId") Long parentId);

}
