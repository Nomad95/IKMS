package pl.politechnika.ikms.service.person.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.politechnika.ikms.commons.abstracts.AbstractService;
import pl.politechnika.ikms.domain.person.ChildEntity;
import pl.politechnika.ikms.repository.person.ChildRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.ChildGeneralDetailDto;
import pl.politechnika.ikms.rest.mapper.person.ChildEntityMapper;
import pl.politechnika.ikms.service.person.ChildService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ChildGeneralDetailDto> getGeneralDto(List<Long> childrenIds) {
        List<ChildEntity> dtos = getRepository().getGeneralDto(childrenIds);
        ArrayList<ChildGeneralDetailDto> generalDtos = dtos.stream()
                .map(childEntityMapper::convertToGeneralDetail)
                .collect(Collectors.toCollection(ArrayList::new));
        return generalDtos;
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
