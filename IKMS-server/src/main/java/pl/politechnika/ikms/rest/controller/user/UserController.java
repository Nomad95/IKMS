package pl.politechnika.ikms.rest.controller.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.politechnika.ikms.commons.util.RandomString;
import pl.politechnika.ikms.commons.util.mail.event.OnRegistrationCompleteEvent;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityMapper;
import pl.politechnika.ikms.rest.mapper.user.UserEntityRegistrationMapper;
import pl.politechnika.ikms.service.user.UserService;

import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final @NonNull ApplicationEventPublisher eventPublisher;
    private final @NonNull UserService userService;
    private final @NonNull UserEntityRegistrationMapper userEntityRegistrationMapper;
    private final @NonNull UserEntityMapper userEntityMapper;

    @GetMapping(value = "/{userId}")
    public UserDto getUser(@PathVariable Long userId){
        return userService.findOne(userId);
    }

    @GetMapping
    public Page<UserDto> getUsers(Pageable pageable){
        return userService.findAllPaginated(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//TODO: creation depends on provided role?
    public UserDto createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto, WebRequest request){
        userRegistrationDto.setPassword(new RandomString(8, ThreadLocalRandom.current()).nextString());
        UserDto userDto = userService.create(userRegistrationDto);
        new Thread(()-> {try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (userRegistrationDto, request.getLocale(), appUrl));
        } catch (Exception me) {
            log.error("Couldn't create email for user " + userDto.getUsername() + ". Deleting user...");
            userService.delete(userDto);
        }}).start();

        return userDto;
    }

    @PutMapping
    public UserDto updateUser(@Valid @RequestBody UserDto userDto){
        return userService.update(userDto);
    }

    @DeleteMapping(value = "/{userId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
