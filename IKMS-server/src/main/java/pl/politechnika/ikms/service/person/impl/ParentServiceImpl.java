package pl.politechnika.ikms.service.person.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ParentEntity;
import pl.politechnika.ikms.repository.person.ParentRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ParentGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ParentEntityMapper;
import pl.politechnika.ikms.service.person.ParentService;

import java.util.List;

@Service
@Transactional
public class ParentServiceImpl extends AbstractService<ParentEntity,ParentRepository> implements ParentService {

    public ParentServiceImpl(ParentRepository repository) {
        super(repository, ParentEntity.class);
    }

    @Override
    public Page<ParentGeneralDetailDto> getParentGeneralDetails(Pageable pageable) {
        Page<ParentEntity> parents = findAllPaginated(pageable);
        return parents.map(e -> ParentEntityMapper.convertToGeneralDetail(e, e.getPersonalData(),e.getPersonalData().getUser()));
    }

    @Override
    public List<MinimalDto<Long, String>> getAllMinimal() {
        return getRepository().getAllMinimalDto();
    }
}
