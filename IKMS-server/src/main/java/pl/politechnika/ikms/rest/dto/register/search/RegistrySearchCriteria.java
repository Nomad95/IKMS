package pl.politechnika.ikms.rest.dto.register.search;

import pl.politechnika.ikms.domain.schedule.ClassesType;
import pl.politechnika.ikms.rest.dto.MinimalDto;

import java.time.LocalDate;


public class RegistrySearchCriteria {

    private MinimalDto<Long, String> child;

    private LocalDate sinceDate;

    private LocalDate toDate;

    private ClassesType classesType;
}
