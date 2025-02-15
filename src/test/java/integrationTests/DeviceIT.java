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
public class DeviceIT {

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

    private List<Long> deviceIds = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        String[] devices = {
                """
                {
                    "name": "Air Conditioner",
                    "totalConsumptionPerHour": 2.5,
                    "roomId": 1
                }
                """,
                """
                {
                    "name": "Smart TV",
                    "totalConsumptionPerHour": 1.2,
                    "roomId": 1
                }
                """
        };

        for (String deviceJson : devices) {
            String response = mockMvc.perform(post("/api/devices")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(deviceJson))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Integer deviceIdInt = JsonPath.read(response, "$.deviceId");
            deviceIds.add(deviceIdInt.longValue());
        }
    }

    @Test
    void testGetAllDevices() throws Exception {
        mockMvc.perform(get("/api/devices"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDeviceById() throws Exception {
        mockMvc.perform(get("/api/devices/" + deviceIds.get(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Air Conditioner"));
    }

    @Test
    void testReturnNotFoundForNonExistentDevice() throws Exception {
        mockMvc.perform(get("/api/devices/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateDevice() throws Exception {
        String updatedDeviceJson = """
            {
                "name": "Updated Smart TV",
                "totalConsumptionPerHour": 1.5,
                "roomId": 1
            }
        """;

        mockMvc.perform(put("/api/devices/" + deviceIds.get(0))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDeviceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Smart TV"));
    }

    @Test
    void testDeleteDevice() throws Exception {
        mockMvc.perform(delete("/api/devices/" + deviceIds.get(0)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/devices/" + deviceIds.get(0)))
                .andExpect(status().isNotFound());
    }
}
