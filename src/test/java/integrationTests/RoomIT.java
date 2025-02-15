package integrationTests;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoomIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private List<Long> roomIds = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        String[] rooms = {
                """
                {
                    "name": "Living Room"
                }
                """,
                """
                {
                    "name": "Kitchen"
                }
                """,
                """
                {
                    "name": "Bedroom"
                }
                """
        };

        for (String roomJson : rooms) {
            String response = mockMvc.perform(post("/api/rooms")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(roomJson))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Integer roomIdInt = JsonPath.read(response, "$.roomId");
            roomIds.add(roomIdInt.longValue());
        }
    }

    @Test
    void testGetAllRooms() throws Exception {
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRoomById() throws Exception {
        mockMvc.perform(get("/api/rooms/" + roomIds.get(0)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Living Room"));
    }

    @Test
    void testReturnNotFoundForNonExistentRoom() throws Exception {
        mockMvc.perform(get("/api/rooms/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRoom() throws Exception {
        String updatedRoomJson = """
            {
                "name": "Master Bedroom"
            }
        """;

        mockMvc.perform(put("/api/rooms/" + roomIds.get(0))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRoomJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Master Bedroom"));
    }

    @Test
    void testDeleteRoom() throws Exception {
        mockMvc.perform(delete("/api/rooms/" + roomIds.get(0)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/rooms/" + roomIds.get(0)))
                .andExpect(status().isNotFound());
    }
}