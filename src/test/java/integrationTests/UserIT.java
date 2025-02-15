package integrationTests;

import application.Application;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@Testcontainers
public class UserIT {

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        mySQLContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
    }

    @Autowired
    private MockMvc mockMvc;

    private List<Long> userIds = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        String[] users = {
                """
            {
                "name": "James",
                "email": "jamesbond@example.com",
                "password": "password123"
            }
            """,
                """
            {
                "name": "Alice",
                "email": "alice@example.com",
                "password": "alicepass"
            }
            """,
                """
            {
                "name": "Bob",
                "email": "bob@example.com",
                "password": "bobpass"
            }
            """
        };

        for (String userJson : users) {
            String response = mockMvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(userJson))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Integer userIdInt = JsonPath.read(response, "$.userId");
            userIds.add(userIdInt.longValue());
        }
    }

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/" + userIds.get(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testReturnNotFoundForNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        String updatedUserJson = """
            {
                "name": "John Updated",
                "email": "john@example.com",
                "password": "newpass"
            }
        """;

        mockMvc.perform(put("/api/users/" + userIds.get(0))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + userIds.get(0)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + userIds.get(0)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        mockMvc.perform(get("/api/users/email/john@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByName() throws Exception {
        mockMvc.perform(get("/api/users/name/John"))
                .andExpect(status().isOk());
    }
}