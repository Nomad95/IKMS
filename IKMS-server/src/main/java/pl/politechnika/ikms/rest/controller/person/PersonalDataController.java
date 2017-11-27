package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.person.PersonalDataEntity;
import pl.politechnika.ikms.rest.dto.MinimalDto;
import pl.politechnika.ikms.rest.dto.person.PersonalDataDto;
import pl.politechnika.ikms.rest.mapper.person.PersonalDataEntityMapper;
import pl.politechnika.ikms.service.person.PersonalDataService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/personalData")
@RequiredArgsConstructor
public class PersonalDataController {

    private final @NonNull PersonalDataEntityMapper personalDataEntityMapper;
    private final @NonNull PersonalDataService personalDataService;

    @GetMapping(value = "/{personalDataId}")
    public PersonalDataDto getOnePersonalData(@PathVariable Long personalDataId){
        return personalDataEntityMapper.convertToDto(personalDataService.findOne(personalDataId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalDataDto createPersonalData(@Valid @RequestBody PersonalDataDto personalDataDto){
        PersonalDataEntity personalDataEntity = personalDataService.create(personalDataEntityMapper.convertToEntity(personalDataDto));
        return personalDataEntityMapper.convertToDto(personalDataEntity);
    }

    @GetMapping
    public Page<PersonalDataDto> getAllPersonalData(Pageable pageable){
        return personalDataService.findAllPaginated(pageable).map(personalDataEntityMapper::convertToDto);
    }

    @PutMapping
    public PersonalDataDto updatePersonalData(@Valid @RequestBody PersonalDataDto personalDataDto){
        PersonalDataEntity personalDataEntity = personalDataService.update(personalDataEntityMapper.convertToEntity(personalDataDto));
        return personalDataEntityMapper.convertToDto(personalDataEntity);
    }

    @DeleteMapping(value = "/{personalDataId}")
    public void deletePersonalData(@PathVariable Long personalDataId){
        personalDataService.deleteById(personalDataId);
    }

    @GetMapping(value = "/myName")
    public ResponseEntity<MinimalDto<Long, String>> getCurrentUserName(HttpServletRequest request){
        String userName = personalDataService.getCurrentUserName(request);
        MinimalDto<Long, String> userDto = new MinimalDto<>(-1L, userName);
        return ResponseEntity.ok(userDto);
    }
}
