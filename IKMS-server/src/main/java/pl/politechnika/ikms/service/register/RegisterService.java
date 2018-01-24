package pl.politechnika.ikms.service.register;

import pl.politechnika.ikms.commons.abstracts.GenericService;
import pl.politechnika.ikms.rest.dto.register.RegisterDto;
import pl.politechnika.ikms.rest.dto.register.RegisterEntryDto;
import pl.politechnika.ikms.rest.dto.register.search.RegistrySearchCriteria;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;

public interface RegisterService extends GenericService<RegisterDto> {
    RegisterDto create(RegisterDto registerDto, Clock clock,  HttpServletRequest request);

    RegisterDto addEntry(RegisterEntryDto registerEntryDto, Clock clock, HttpServletRequest request);

    RegisterDto getRegistryByCriteria(RegistrySearchCriteria registrySearchCriteria);
}
