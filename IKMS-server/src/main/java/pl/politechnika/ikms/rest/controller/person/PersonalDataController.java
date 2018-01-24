package pl.politechnika.ikms.rest.controller.person;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private final @NonNull PersonalDataService personalDataService;

    @GetMapping(value = "/{personalDataId}")
    public PersonalDataDto getOnePersonalData(@PathVariable Long personalDataId){
        return personalDataService.findOne(personalDataId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalDataDto createPersonalData(@Valid @RequestBody PersonalDataDto personalDataDto){
        return personalDataService.create(personalDataDto);
    }

    @GetMapping
    public Page<PersonalDataDto> getAllPersonalData(Pageable pageable){
        return personalDataService.findAllPaginated(pageable);
    }

    @PutMapping
    public PersonalDataDto updatePersonalData(@Valid @RequestBody PersonalDataDto personalDataDto){
        return personalDataService.update(personalDataDto);
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
