package pl.politechnika.ikms.rest.controller.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.user.UserEntity;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityMapper;
import pl.politechnika.ikms.rest.mapper.user.UserEntityRegistrationMapper;
import pl.politechnika.ikms.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final @NonNull UserService userService;
    private final @NonNull UserEntityRegistrationMapper userEntityRegistrationMapper;
    private final @NonNull UserEntityMapper userEntityMapper;

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public UserDto getUser(@PathVariable Long userId){
        return userEntityMapper.convertToDto(userService.findOne(userId));
    }

    @GetMapping
    @ResponseBody
    public Page<UserDto> getUsers(Pageable pageable){
        Page<UserEntity> allPaginated = userService.findAllPaginated(pageable);
        return allPaginated.map(userEntityMapper::convertToDto);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)//TODO: creation depends on provided role?
    public UserDto createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        UserEntity user = userEntityRegistrationMapper.convertToEntity(userRegistrationDto);
        UserEntity createdUser = userService.create(user);
        return userEntityMapper.convertToDto(createdUser);
    }

    @PutMapping
    @ResponseBody
    public UserDto updateUser(@Valid @RequestBody UserDto userDto){
        UserEntity user = userEntityMapper.convertToEntity(userDto);
        UserEntity updatedUser = userService.update(user);
        return userEntityMapper.convertToDto(updatedUser);
    }

    @DeleteMapping(value = "/{userId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
