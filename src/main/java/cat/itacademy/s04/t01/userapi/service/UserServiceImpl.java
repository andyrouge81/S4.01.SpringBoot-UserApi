package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.entities.User;
import cat.itacademy.s04.t01.userapi.exceptions.UserAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo){
        this.repo = repo;
    }

    @Override
    public User createUser(User user){

        if (user == null){
            throw new IllegalArgumentException("User Empty");
        }

        validateEmail(user.getEmail());

        if(repo.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        user.setId(UUID.randomUUID());

        return repo.save(user);


    }

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    public User searchUserById(UUID id){
        if(id == null){
            throw new IllegalArgumentException("Error: id must not be null");
        }

        Optional<User> result = repo.findById(id);

        if(result.isEmpty()){
            throw new UserNotFoundException("Error searching user by id :"+ id);

        }

        return result.get();

    }

    @Override
    public List<User> searchUserByName(String name){
        if(name == null){
            throw new IllegalArgumentException("Error: name must not be null");
        }

        if(name.isBlank()){
            throw new IllegalArgumentException("Error: name cannot be empty");
        }

        List<User> findUser = repo.searchByName(name);


        return findUser;

    }

    @Override
    public User searchUserByEmail(String email){

        if (email == null) {
            throw new IllegalArgumentException("Error: email should not be null");
        }

        if (email.isBlank()){
            throw new IllegalArgumentException("Error: email should not be empty");
        }

        Optional<User> foundUser = repo.findByEmail(email);

        if(foundUser.isEmpty()){
            throw new UserNotFoundException("Error: User not found wit email: "+email);
        }

        return foundUser.get();

    }

    @Override
    public void deleteUserById(UUID id){

        if (id == null) {
            throw new IllegalArgumentException("Error: User id should not be null");
        }

        Optional<User> foundUser = repo.findById(id);

        if (foundUser.isEmpty()) {

            throw new UserNotFoundException("Error: User not found wit id: "+id);

        }

        repo.deleteById(id);

    }

    private void validateEmail(String email){
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email could nor be null or empty");
        }
    }
}
