package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
        String jsonBody = mapper.writeValueAsString(body);

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
    void getUserById_returnsCorrectUser() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("name", "choripan");
        body.put("email", "choripa@example.com");
        String jsonBody = mapper.writeValueAsString(body);

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))

                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        User createUser = mapper.readValue(responseJson, User.class);

        UUID id = createUser.getId();

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("choripan"));

    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception{
        Map<String, String> body = new HashMap<>();
        body.put("name", "ramon");
        body.put("email", "ramoncin@example.com");

        String jsonBody = mapper.writeValueAsString(body);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                 .content(jsonBody))
                .andExpect(status().isOk());

        UUID id = UUID.randomUUID();


        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());

    }

}