package pl.politechnika.ikms.rest.controller.user;

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
@RequestMapping(value = "/api/user")
public class UserController {

    private final @NonNull UserService userService;

    @RequestMapping(value = "/{userId}")
    public @ResponseBody User getUser(@PathVariable Long userId){
        return userService.findOne(userId);
    }
}
