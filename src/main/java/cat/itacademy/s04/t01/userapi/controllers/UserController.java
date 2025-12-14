package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import cat.itacademy.s04.t01.userapi.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
public class UserController {

    //private static List<User> users = new ArrayList<>();
    private final UserService userService;

    public UserController(UserService userservice){
        this.userService = userservice;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id){

        return userService.searchUserById(id);
    }

    @GetMapping("/users")
    public List<User> getUserByName(@RequestParam(required = false) String name){
        if(name == null){
            return userService.getAllUsers();
        }

        return userService.searchUserByName(name);
    }


}
