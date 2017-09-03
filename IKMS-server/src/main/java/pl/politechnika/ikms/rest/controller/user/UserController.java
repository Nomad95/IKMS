package pl.politechnika.ikms.rest.controller.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.rest.dto.user.UserDto;
import pl.politechnika.ikms.rest.dto.user.UserRegistrationDto;
import pl.politechnika.ikms.rest.mapper.user.UserEntityToDtoMapper;
import pl.politechnika.ikms.rest.mapper.user.UserEntityToRegDtoMapper;
import pl.politechnika.ikms.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {

    private final @NonNull UserService userService;
    private final @NonNull UserEntityToRegDtoMapper userEntityToRegDtoMapper;
    private final @NonNull UserEntityToDtoMapper userEntityToDtoMapper;

    @GetMapping(value = "/{userId}")
    @ResponseBody
    public UserDto getUser(@PathVariable Long userId){
        return userEntityToDtoMapper.convertToDto(userService.findOne(userId));
    }

    @GetMapping(params = { "page", "size" })//TODO: uzyj Page<>
    @ResponseBody
    public List<UserDto> getUsers(@RequestParam("page") int page, @RequestParam("size") int size){
        List<User> paginatedUsers = userService.findAllPaginated(page, size, Optional.empty()).getContent();//TODO: null here
        return paginatedUsers.stream().map(userEntityToDtoMapper::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)//TODO: creation depends on provided role
    public UserDto createUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        User user = userEntityToRegDtoMapper.convertToEntity(userRegistrationDto);
        User createdUser = userService.create(user);
        return userEntityToDtoMapper.convertToDto(createdUser);
    }

    @PutMapping
    @ResponseBody
    public UserDto updateUser(@Valid @RequestBody UserDto userDto){
        User user = userEntityToDtoMapper.convertToEntity(userDto);
        User updatedUser = userService.update(user);
        return userEntityToDtoMapper.convertToDto(updatedUser);
    }

    @DeleteMapping(value = "/{userId}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }
}
