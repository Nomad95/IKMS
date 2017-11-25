package pl.politechnika.ikms.rest.mapper.schedule;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.politechnika.ikms.commons.abstracts.AbstractModelMapper;
import pl.politechnika.ikms.domain.schedule.ScheduleActivityError;
import pl.politechnika.ikms.repository.schedule.ScheduleActivityRepository;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.schedule.ScheduleActivityErrorDto;

import java.util.Objects;

@Component
public class ScheduleActivityErrorMapper extends AbstractModelMapper<ScheduleActivityError, ScheduleActivityErrorDto> {


    private final @NonNull ScheduleActivityRepository scheduleActivityRepository;

    public ScheduleActivityErrorMapper(
            ModelMapper modelMapper,
            ScheduleActivityRepository scheduleActivityRepository) {
        super(modelMapper);
        this.scheduleActivityRepository = scheduleActivityRepository;
    }


    @Override
    public ScheduleActivityErrorDto convertToDto(ScheduleActivityError scheduleActivityError) {
        ScheduleActivityErrorDto dto = modelMapper.map(scheduleActivityError, ScheduleActivityErrorDto.class);

        if (Objects.nonNull(scheduleActivityError.getActivity())) {
            dto.setActivity( new MinimalDto<>(scheduleActivityError.getId(), scheduleActivityError.getMessage()));
        }

        return dto;
    }

    @Override
    public ScheduleActivityError convertToEntity(ScheduleActivityErrorDto dto) {
        ScheduleActivityError entity = modelMapper.map(dto, ScheduleActivityError.class);

        if (Objects.nonNull(dto.getActivity())) {
            entity.setActivity(scheduleActivityRepository.getOne(dto.getActivity().getId()));
        }

        return entity;
    }
}
