package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(User user);
    List<User> getAllUsers();
    List<User> searchUserByName(String name);
    User searchUserById(UUID id);
    User searchUserByEmail(String email);
    void deleteUserById(UUID id);

}
