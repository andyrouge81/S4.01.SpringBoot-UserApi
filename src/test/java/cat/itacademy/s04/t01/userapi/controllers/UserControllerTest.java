package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;



    @Test
    void getUsers_returnEmptyListInitially() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void createUser_returnsUserFromService() throws Exception {

        //given
        User user = new User(UUID.randomUUID(), "Pepito","pepito@example.com");
        String jsonBody = mapper.writeValueAsString(user);

        //when
        when(userService.createUser(any(User.class)))
                .thenReturn(user);

        //then
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andExpect(jsonPath("$.email").value("pepito@example.com"));

    }


    @Test
    void getUserById_returnsUserFromService() throws Exception {

        UUID id = UUID.randomUUID();
        User user1 = new User(id
                ,"choripan", "choripa@example.com" );


        when(userService.searchUserById(id))
                .thenReturn(user1);


        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("choripan"));

    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception{


        UUID id = UUID.randomUUID();

        when(userService.searchUserById(id))
                .thenThrow(new UserNotFoundException(" User Not Found"));

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());

    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {

        List<User> usersList = List.of(new User(UUID.randomUUID(), "Charjo", "charjo@example.com"),
                new User(UUID.randomUUID(), "Jolian", "jolijoli@example.com"));

        when(userService.searchUserByName("jo"))
                .thenReturn(usersList);


        mockMvc.perform(get("/users").param("name","jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", containsStringIgnoringCase("jo")))
                .andExpect(jsonPath("$[1].name", containsStringIgnoringCase("jo")))
                .andExpect(jsonPath("$.length()").value(2));


    }

    @Test
    void getAllUsers_withoutNameParam_getAllUsers() throws Exception{

        when(userService.getAllUsers())
                .thenReturn(List.of());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        Mockito.verify(userService).getAllUsers();
    }

}