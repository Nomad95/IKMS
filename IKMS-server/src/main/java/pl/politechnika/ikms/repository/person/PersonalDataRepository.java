package pl.politechnika.ikms.repository.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;


@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalDataEntity, Long>{

    @Query(value = "select pd.name, pd.surname from personal_data pd " +
            "join users u ON u.id = pd.id where u.username = ?1",
            nativeQuery = true)
    String findNameAndSurNameSeparatedByComma(String username);

    @Query(value = "select concat(pd.name,' ', pd.surname) from PersonalDataEntity pd " +
            "join pd.user us " +
            "where us.id = :userId")
    String findCurrentUserName(@Param("userId") Long userId);

}
