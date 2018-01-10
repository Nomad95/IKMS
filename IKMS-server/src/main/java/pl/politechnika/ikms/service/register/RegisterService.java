package pl.politechnika.ikms.service.register;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.domain.register.RegisterEntity;
import pl.politechnika.ikms.domain.register.RegisterEntryEntity;
import pl.politechnika.ikms.rest.dto.register.RegisterDto;
import pl.politechnika.ikms.rest.dto.register.RegisterEntryDto;
import pl.politechnika.ikms.rest.dto.register.search.RegistrySearchCriteria;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;

public interface RegisterService extends GenericService<RegisterEntity> {
    RegisterEntity create(RegisterEntity registerEntity, Clock clock,  HttpServletRequest request);

    RegisterEntity addEntry(RegisterEntryEntity registerEntryEntity, Clock clock, HttpServletRequest request);

    RegisterEntity getRegistryByCriteria(RegistrySearchCriteria registrySearchCriteria);
}
