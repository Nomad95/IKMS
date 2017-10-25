package pl.politechnika.ikms.service.person.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.service.person.ChildService;

import javax.validation.constraints.NotNull;

@Service
public class ChildServiceImpl extends AbstractService<ChildEntity,ChildRepository> implements ChildService {

    private final @NotNull ChildEntityMapper childEntityMapper;

    public ChildServiceImpl(ChildRepository repository, ChildEntityMapper childEntityMapper) {
        super(repository, ChildEntity.class);
        this.childEntityMapper = childEntityMapper;
    }

    @Override
    public Page<ChildGeneralDetailDto> getChildGeneralDetail(Pageable pageable) {
        Page<ChildEntity> childrens = findAllPaginated(pageable);
        return childrens.map(childEntityMapper::convertToGeneralDetail);
    }
}
