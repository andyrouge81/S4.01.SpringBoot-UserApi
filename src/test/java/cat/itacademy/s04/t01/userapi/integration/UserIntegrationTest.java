package cat.itacademy.s04.t01.userapi.integration;

import cat.itacademy.s04.t01.userapi.entities.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserIntegrationTest {

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

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {

        Map<String, String> body = new HashMap<>();
        body.put("name", "Charjo");
        body.put("email", "coxis@example.com");



        String jsonBody = mapper.writeValueAsString(body);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());

        Map<String, String> body2 = new HashMap<>();
        body2.put("name", "jolian");
        body2.put("email", "folixa@example.com");

        String jsonBody2 = mapper.writeValueAsString(body2);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody2))
                .andExpect(status().isOk());



        mockMvc.perform(get("/users").param("name","jo"))

                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name", containsStringIgnoringCase("jo")))
                .andExpect(jsonPath("$[1].name", containsStringIgnoringCase("jo")))
                .andExpect(jsonPath("$.length()").value(2));


    }

}