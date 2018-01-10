package pl.politechnika.ikms.rest.controller.register;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.register.RegisterEntity;
import pl.politechnika.ikms.rest.dto.register.RegisterDto;
import pl.politechnika.ikms.rest.dto.register.RegisterEntryDto;
import pl.politechnika.ikms.rest.dto.register.search.RegistrySearchCriteria;
import pl.politechnika.ikms.rest.mapper.register.RegisterEntityMapper;
import pl.politechnika.ikms.rest.mapper.register.RegisterEntryEntityMapper;
import pl.politechnika.ikms.service.register.RegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Clock;

import static java.time.Clock.systemUTC;

@RestController
@RequestMapping(value = "/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final @NonNull
    RegisterService registerService;
    private final @NonNull
    RegisterEntityMapper registerEntityMapper; //TODO: [aboruch] trzeba w późniejszym czasie zmienić żeby api od dziennika
    // i innych api przykrywało entity ( we wszystkich controllerach jest jak tu )
    private final @NonNull
    RegisterEntryEntityMapper registerEntryEntityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDto createRegisterWithPresence(@Valid @RequestBody RegisterDto registerDto, HttpServletRequest request){
        System.out.println("Co do ...");
        Clock clock = systemUTC();
        RegisterEntity registerEntity = registerService.create(registerEntityMapper.convertToEntity(registerDto), clock, request);

        return registerEntityMapper.convertToDto(registerEntity);
    }

    @PostMapping(value = "/entry")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegisterDto addEntryToRegister(@Valid @RequestBody RegisterEntryDto registerEntryDto, HttpServletRequest request){
        Clock clock = systemUTC();
        RegisterEntity registerEntity = registerService.addEntry(registerEntryEntityMapper.convertToEntity(registerEntryDto), clock, request);

        return registerEntityMapper.convertToDto(registerEntity);
    }

    @GetMapping()
    public RegisterDto getRegisterForChild(@Valid @RequestBody RegistrySearchCriteria registrySearchCriteria){
        RegisterEntity registerEntity = registerService.getRegistryByCriteria(registrySearchCriteria);


        return null;
    }
}
