package pl.politechnika.ikms.service.person.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.service.person.ChildService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl extends AbstractService<ChildEntity, ChildDto, ChildRepository, ChildEntityMapper> implements ChildService {

    public ChildServiceImpl(ChildRepository repository, ChildEntityMapper converter) {
        super(repository, converter, ChildEntity.class);
    }

    @Override
    public Page<ChildGeneralDetailDto> getChildGeneralDetail(Pageable pageable) {
        return getRepository().findAll(pageable).map(getConverter()::convertToGeneralDetail);
    }

    @Override
    public List<ChildGeneralDetailDto> getGeneralDto(List<Long> childrenIds) {
        List<ChildEntity> dtos = getRepository().getGeneralDto(childrenIds);
        return dtos.stream()
                .map(getConverter()::convertToGeneralDetail)
                .collect(Collectors.toList());
    }

    @Override
    public List<MinimalDto<Long, String>> getGrouplessChildrenMinimal() {
        return getRepository().getGrouplessChildrenMinimal();
    }

    @Override
    public List<MinimalDto<Long, String>> getChildrenMinimal() {
        return getRepository().getChildrenMinimal();
    }
}
