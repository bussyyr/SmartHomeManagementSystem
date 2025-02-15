package persistenceTests;

import application.Application;
import domain.models.*;
import infrastructure.api.dto.*;
import infrastructure.persistence.entities.*;
import infrastructure.persistence.mapper.*;
import infrastructure.persistence.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Testcontainers
public class PersistenceComponentTest {

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
    }

    @Autowired private AutomationRuleRepository automationRuleRepository;
    @Autowired private DeviceRepository deviceRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private EnergyReportRepository energyReportRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AutomationRuleMapper automationRuleMapper;
    @Autowired private DeviceMapper deviceMapper;
    @Autowired private RoomMapper roomMapper;
    @Autowired private EnergyReportMapper energyReportMapper;
    @Autowired private UserMapper userMapper;

    @Test
    void testCreateAndRetrieveEntities() {
        // Automation Rule
        AutomationRuleEntity rule = new AutomationRuleEntity();
        rule.setName("Energy Saver");
        automationRuleRepository.save(rule);
        assertTrue(automationRuleRepository.findById(rule.getAutomationRuleId()).isPresent());

        // Device
        DeviceEntity device = new DeviceEntity();
        device.setName("Smart TV");
        deviceRepository.save(device);
        assertTrue(deviceRepository.findById(device.getDeviceId()).isPresent());

        // Room
        RoomEntity room = new RoomEntity();
        room.setName("Living Room");
        roomRepository.save(room);
        assertTrue(roomRepository.findById(room.getRoomId()).isPresent());

        // User
        UserEntity user = new UserEntity();
        user.setName("John Doe");
        userRepository.save(user);
        assertTrue(userRepository.findById(user.getUserId()).isPresent());

        // Energy Report
        EnergyReportEntity report = new EnergyReportEntity();
        report.setTotalConsumption(10.5);
        energyReportRepository.save(report);
        assertTrue(energyReportRepository.findById(report.getEnergyReportId()).isPresent());
    }

    @Test
    void testMapperConversions() {
        // AutomationRule Mapping
        AutomationRuleInput automationRuleInput = new AutomationRuleInput();
        automationRuleInput.setName("Eco Mode");
        AutomationRule automationRule = automationRuleMapper.inputToDomain(automationRuleInput);
        assertEquals("Eco Mode", automationRule.getName());

        // Device Mapping
        DeviceInput deviceInput = new DeviceInput();
        deviceInput.setName("Smart TV");
        Device device = deviceMapper.inputToDomain(deviceInput);
        assertEquals("Smart TV", device.getName());

        // EnergyReport Mapping
        EnergyReportInput energyReportInput = new EnergyReportInput();
        energyReportInput.setTotalConsumption(12.5);
        EnergyReport energyReport = energyReportMapper.inputToDomain(energyReportInput);
        assertEquals(12.5, energyReport.getTotalConsumption());

        // Room Mapping
        RoomInput roomInput = new RoomInput();
        roomInput.setName("Living Room");
        Room room = roomMapper.inputToDomain(roomInput);
        assertEquals("Living Room", room.getName());

        // User Mapping
        UserInput userInput = new UserInput();
        userInput.setName("John Doe");
        User user = userMapper.inputToDomain(userInput);
        assertEquals("John Doe", user.getName());
    }


    @Test
    void testUpdateEntities() {
        // Update Automation Rule
        AutomationRuleEntity rule = new AutomationRuleEntity();
        rule.setName("Eco Mode");
        automationRuleRepository.save(rule);

        rule.setName("Power Saver Mode");
        automationRuleRepository.save(rule);
        assertEquals("Power Saver Mode", automationRuleRepository.findById(rule.getAutomationRuleId()).get().getName());

        // Update Device
        DeviceEntity device = new DeviceEntity();
        device.setName("Lamp");
        deviceRepository.save(device);

        device.setName("Smart Lamp");
        deviceRepository.save(device);
        assertEquals("Smart Lamp", deviceRepository.findById(device.getDeviceId()).get().getName());

        // Update Room
        RoomEntity room = new RoomEntity();
        room.setName("Kitchen");
        roomRepository.save(room);

        room.setName("Updated Kitchen");
        roomRepository.save(room);
        assertEquals("Updated Kitchen", roomRepository.findById(room.getRoomId()).get().getName());

        // Update User
        UserEntity user = new UserEntity();
        user.setName("Alice");
        userRepository.save(user);

        user.setName("Alice Updated");
        userRepository.save(user);
        assertEquals("Alice Updated", userRepository.findById(user.getUserId()).get().getName());

        // Update Energy Report
        EnergyReportEntity report = new EnergyReportEntity();
        report.setTotalConsumption(8.4);
        energyReportRepository.save(report);

        report.setTotalConsumption(10.0);
        energyReportRepository.save(report);
        assertEquals(10.0, energyReportRepository.findById(report.getEnergyReportId()).get().getTotalConsumption());
    }

    @Test
    void testDeleteEntities() {
        // Delete Automation Rule
        AutomationRuleEntity rule = new AutomationRuleEntity();
        rule.setName("Night Saver");
        automationRuleRepository.save(rule);
        Long ruleId = rule.getAutomationRuleId();

        automationRuleRepository.deleteById(ruleId);
        assertFalse(automationRuleRepository.findById(ruleId).isPresent());

        // Delete Device
        DeviceEntity device = new DeviceEntity();
        device.setName("Fan");
        deviceRepository.save(device);
        Long deviceId = device.getDeviceId();

        deviceRepository.deleteById(deviceId);
        assertFalse(deviceRepository.findById(deviceId).isPresent());

        // Delete Room
        RoomEntity room = new RoomEntity();
        room.setName("Bedroom");
        roomRepository.save(room);
        Long roomId = room.getRoomId();

        roomRepository.deleteById(roomId);
        assertFalse(roomRepository.findById(roomId).isPresent());

        // Delete User
        UserEntity user = new UserEntity();
        user.setName("Bob");
        userRepository.save(user);
        Long userId = user.getUserId();

        userRepository.deleteById(userId);
        assertFalse(userRepository.findById(userId).isPresent());

        // Delete Energy Report
        EnergyReportEntity report = new EnergyReportEntity();
        report.setTotalConsumption(5.5);
        energyReportRepository.save(report);
        Long reportId = report.getEnergyReportId();

        energyReportRepository.deleteById(reportId);
        assertFalse(energyReportRepository.findById(reportId).isPresent());
    }

}
