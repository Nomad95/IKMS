package pl.politechnika.ikms.rest.controller.register;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterDto createRegisterWithPresence(@Valid @RequestBody RegisterDto registerDto,
            HttpServletRequest request) {
        Clock clock = systemUTC();
        return registerService.create(registerDto, clock, request);
    }

    @PostMapping(value = "/entry")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RegisterDto addEntryToRegister(@Valid @RequestBody RegisterEntryDto registerEntryDto,
            HttpServletRequest request) {
        Clock clock = systemUTC();
        return registerService.addEntry(registerEntryDto, clock, request);
    }

    @GetMapping()
    public RegisterDto getRegisterForChild(@Valid @RequestBody RegistrySearchCriteria registrySearchCriteria) {
        RegisterDto registryByCriteria = registerService.getRegistryByCriteria(registrySearchCriteria);


        return null;
    }
}
