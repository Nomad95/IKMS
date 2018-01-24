package pl.politechnika.ikms.service.person;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;

import javax.servlet.http.HttpServletRequest;

public interface PersonalDataService extends GenericService<PersonalDataDto> {

    String getCurrentUserName(HttpServletRequest request);
}
