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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
@Testcontainers
public class EnergyReportIT {

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

    private List<Long> reportIds = new ArrayList<>();
    private final LocalDate fixedDate = LocalDate.of(2023, 10, 15);

    @BeforeEach
    void setUp() throws Exception {
        String[] reports = {
                """
                {
                    "deviceId": 1,
                    "totalConsumption": 3.5,
                    "date": "%s"
                }
                """.formatted(fixedDate),
                """
                {
                    "deviceId": 2,
                    "totalConsumption": 5.2,
                    "date": "%s"
                }
                """.formatted(fixedDate)
        };

        for (String reportJson : reports) {
            String response = mockMvc.perform(post("/api/energy_reports")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(reportJson))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Integer reportIdInt = JsonPath.read(response, "$.energyReportId");
            reportIds.add(reportIdInt.longValue());
        }
    }

    @Test
    void testGetAllEnergyReports() throws Exception {
        mockMvc.perform(get("/api/energy_reports"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEnergyReportById() throws Exception {
        mockMvc.perform(get("/api/energy_reports/" + reportIds.get(0)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEnergyReportByDate() throws Exception {
        mockMvc.perform(get("/api/energy_reports/date/" + fixedDate.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateEnergyReport() throws Exception {
        String newReportJson = """
            {
                "deviceId": 3,
                "totalConsumption": 4.8,
                "date": "%s"
            }
        """.formatted(fixedDate);

        mockMvc.perform(post("/api/energy_reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newReportJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testReturnNotFoundForNonExistentEnergyReport() throws Exception {
        mockMvc.perform(get("/api/energy_reports/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEnergyReport() throws Exception {
        mockMvc.perform(delete("/api/energy_reports/" + reportIds.get(0)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/energy_reports/" + reportIds.get(0)))
                .andExpect(status().isNotFound());
    }
}
