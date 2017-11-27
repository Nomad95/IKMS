package pl.politechnika.ikms.service.person.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.exceptions.EntityNotFoundException;
import pl.politechnika.ikms.repository.person.PersonalDataRepository;
import pl.politechnika.ikms.security.JwtUserFacilities;
import pl.politechnika.ikms.service.person.PersonalDataService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class PersonalDataServiceImpl extends AbstractService<PersonalDataEntity, PersonalDataRepository> implements PersonalDataService{

    private final @NonNull JwtUserFacilities jwtUserFacilities;

    public PersonalDataServiceImpl(
            PersonalDataRepository repository,
            JwtUserFacilities jwtUserFacilities) {
        super(repository, PersonalDataEntity.class);
        this.jwtUserFacilities = jwtUserFacilities;
    }

    @Override
    @Transactional
    public String getCurrentUserName(HttpServletRequest request) {
        UserEntity user = jwtUserFacilities.findUserByUsernameFromToken(request);
        if (!Objects.nonNull(user))
            throw new EntityNotFoundException("Nie znaleziono usera " + jwtUserFacilities.pullTokenAndGetUsername(request));
        return getRepository().findCurrentUserName(user.getId());
    }
}
