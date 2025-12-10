package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getUsers_returnEmptyListInitially() throws Exception{
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void createUser_returnsUserWithId() throws Exception {

        Map<String, String> body = new HashMap<>();
        body.put("name", "Pepito");
        body.put("email", "pepito@example.com");
        String jsonMapper = mapper.writeValueAsString(body);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Pepito"))
                .andExpect(jsonPath("$.email").value("pepito@example.com"));


    }


    @Test
    void getUserById_returnsCorrectUser() throws Exception{
        Map<String, String> body = new HashMap<>();
        body.put("name","choripan");
        body.put("email","choripa@example.com");
        String objectMapper = mapper.writeValueAsString(body);

        mockMvc.perform(get("/users/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("choripan"));

        // Primer afegeix un usuari amb POST

        // Després GET /users/{id} i comprova que torni aquest usuari
    }

}