package pl.politechnika.ikms.service.person;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;

import javax.servlet.http.HttpServletRequest;

public interface PersonalDataService extends GenericService<PersonalDataEntity> {

    String getCurrentUserName(HttpServletRequest request);
}
