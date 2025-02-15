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
public class AutomationRuleIT {

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

    private List<Long> ruleIds = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        String[] rules = {
                """
                {
                    "name": "Night Mode",
                    "ruleType": "TIME",
                    "devices": [1, 2],
                    "consumptionLimit": null
                }
                """,
                """
                {
                    "name": "Energy Saver",
                    "ruleType": "CONSUMPTION",
                    "devices": [3],
                    "consumptionLimit": 10.0
                }
                """
        };

        for (String ruleJson : rules) {
            String response = mockMvc.perform(post("/api/automation_rules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ruleJson))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Integer ruleIdInt = JsonPath.read(response, "$.automationRuleId");
            ruleIds.add(ruleIdInt.longValue());
        }
    }

    @Test
    void testGetAllAutomationRules() throws Exception {
        mockMvc.perform(get("/api/automation_rules"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAutomationRuleById() throws Exception {
        mockMvc.perform(get("/api/automation_rules/" + ruleIds.get(0)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateAutomationRule() throws Exception {
        String newRuleJson = """
            {
                "name": "Peak Hours Shutdown",
                "ruleType": "TIME",
                "devices": [4],
                "consumptionLimit": null
            }
        """;

        mockMvc.perform(post("/api/automation_rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRuleJson))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateAutomationRule() throws Exception {
        String updatedRuleJson = """
            {
                "name": "Updated Energy Saver",
                "ruleType": "CONSUMPTION",
                "devices": [3],
                "consumptionLimit": 8.0
            }
        """;

        mockMvc.perform(put("/api/automation_rules/" + ruleIds.get(0))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRuleJson))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAutomationRule() throws Exception {
        mockMvc.perform(delete("/api/automation_rules/" + ruleIds.get(0)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/automation_rules/" + ruleIds.get(0)))
                .andExpect(status().isNotFound());
    }
}
