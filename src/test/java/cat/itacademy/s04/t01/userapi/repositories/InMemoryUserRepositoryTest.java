package cat.itacademy.s04.t01.userapi.repositories;

import cat.itacademy.s04.t01.userapi.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repo;
    private UUID id;

    @BeforeEach
    void test(){
        repo = new InMemoryUserRepository();
        id = UUID.randomUUID();
    }

    @Test
    void findAll_returnEmptyListInitially(){

        List<User> userList = repo.findAll();

        assertTrue(userList.isEmpty());
        assertNotSame(userList, repo.findAll());
    }

    @Test
    void saveUser_addNewUserToList(){
        User user1 = new User(id, "Thomas", "melomano@example.com");

        repo.save(user1);

        List<User> userList = repo.findAll();

        assertEquals(1, userList.size());
        assertEquals(user1, userList.get(0));
    }

    @Test
    void findById_returnAnUserSearchById(){

        User user2 = new User(id, "Thomas", "melomano@example.com");

        repo.save(user2);

        assertTrue(repo.findById(id).isPresent());

        assertEquals(Optional.of(user2), repo.findById(id));

    }

    @Test
    void findById_whenAddingIncorrectId_returnOptionalIsEmpty(){
        User user3 = new User(id, "Manuel", "manolocabesa@example.com");

        repo.save(user3);

        UUID id2 = UUID.randomUUID();

        Optional<User> result = repo.findById(id2);

        assertTrue(result.isEmpty());


    }

    @Test
    void searchByName_givenAStringName_matchWithUserFromTheRepoList(){

        User user4 = new User(UUID.randomUUID(), "Paconi", "pacon@example.com");

        User user5 = new User(UUID.randomUUID(), "Joana", "xonya@example.com");

        //List<User> userList = repo.searchByName("co");

        repo.save(user4);
        repo.save(user5);


        assertEquals(1, repo.searchByName("CO").size());
        assertEquals("Paconi", repo.searchByName("co").get(0).getName());
        assertTrue(repo.searchByName("ca").isEmpty());


    }

    @Test
    void existsByEmail_returnTrueWhenEmailExists(){

        User user6 = new User(UUID.randomUUID(), "Joana", "jopetas@example.com");

        repo.save(user6);

        //boolean result = repo.searchUserByEmail("JOPETAS@example.com");

        //assertTrue(result);


    }

    @Test
    void existsByEmail_returnEmptyOptionalWhenEmailDoesNotExists(){

        User user6 = new User(UUID.randomUUID(), "Joana", "jopetas@example.com");

        repo.save(user6);

        Optional<User> result = repo.findByEmail("ChAMACO@example.com");

        assertTrue(result.isEmpty());


    }



}