package pl.politechnika.ikms.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.politechnika.ikms.domain.user.User;
import pl.politechnika.ikms.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final @NonNull UserService userService;

    //TODO: change this after tests
    @RequestMapping(value = "/test/{userId}")
    public @ResponseBody User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }
}
