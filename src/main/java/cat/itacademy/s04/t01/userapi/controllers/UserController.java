package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@RestController
public class UserController {

    private static List<User> users = new ArrayList<>();

    /*
    @GetMapping("/users")
    public List<User> getUsers(){
        return users;
    }*/

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        user.setId(UUID.randomUUID());
        users.add(user);
        return user;
    }
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id){
        return users.stream()
                .filter(u->u.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new UserNotFoundException("NotFound(404)"));

    }

    @GetMapping("/users")
    public List<User> getUserByName(@RequestParam(required = false) String name){
        if(name == null){
            return users;
        }

        return users.stream()
                .filter(n->n.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
