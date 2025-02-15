package CRUDOperations;

import domain.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DomainModelsTest {

    //Automation Rule
    @Test
    void testCreateAutomationRule() {
        AutomationRule rule = new AutomationRule(101L, "Night Mode", AutomationRuleType.TIME, 75.5);
        assertEquals(101L, rule.getAutomationRuleId());
        assertEquals("Night Mode", rule.getName());
        assertEquals(AutomationRuleType.TIME, rule.getRuleType());
        assertEquals(75.5, rule.getConsumptionLimit());
        assertTrue(rule.getDevices().isEmpty());
    }

    @Test
    void testUpdateAutomationRule_ChangeNameAndLimit() {
        AutomationRule rule = new AutomationRule(102L, "Eco Mode", AutomationRuleType.CONSUMPTION, 50.0);
        rule.setName("Updated Eco Mode");
        rule.setConsumptionLimit(40.0);
        assertEquals("Updated Eco Mode", rule.getName());
        assertEquals(40.0, rule.getConsumptionLimit());
    }

    //Device
    @Test
    void testCreateDevice() {
        Room room = new Room(201L, "Living Room", null);
        AutomationRule rule = new AutomationRule(101L, "Energy Saver", AutomationRuleType.TIME, 50.0);
        Device device = new Device(202L, 15.0, "Smart Light", rule, room);

        assertEquals(202L, device.getDeviceId());
        assertEquals("Smart Light", device.getName());
        assertEquals(15.0, device.getTotalConsumptionPerHour());
        assertFalse(device.isStatus());
        assertEquals(room, device.getRoom());
        assertEquals(rule, device.getAutomationRule());
        assertEquals(360.0, device.getDailyConsumption()); // 15 * 24
        assertTrue(device.getEnergyReports().isEmpty());
    }

    @Test
    void testUpdateDevice_ChangeNameAndConsumption() {
        Device device = new Device(203L, 20.0, "Old Device", null, null);
        device.setName("Updated Device");
        device.setTotalConsumptionPerHour(25.0);
        device.setStatus(true);

        assertEquals("Updated Device", device.getName());
        assertEquals(25.0, device.getTotalConsumptionPerHour());
        assertTrue(device.isStatus());
    }

    @Test
    void testDeviceAutomationRule() {
        AutomationRule rule = new AutomationRule(501L, "Night Mode", AutomationRuleType.TIME, 75.0);
        Device device = new Device(202L, 15.0, "Smart Light", rule, null);

        assertEquals(rule, device.getAutomationRule());
    }

    @Test
    void testDeviceEnergyReports() {
        Device device = new Device(203L, 20.0, "Fridge", null, null);

        assertTrue(device.getEnergyReports().isEmpty());

        EnergyReport report = new EnergyReport(301L, LocalDate.of(2024, 2, 15), device, 120.5);
        device.getEnergyReports().add(report);

        assertEquals(1, device.getEnergyReports().size());
        assertEquals(report, device.getEnergyReports().get(0));
    }

    @Test
    void testDeviceDailyConsumptionCalculation() {
        Device device = new Device(204L, 10.0, "Air Conditioner", null, null);
        assertEquals(240.0, device.getDailyConsumption());  // 10.0 * 24
    }

    //Energy Report
    @Test
    void testCreateEnergyReport() {
        Device device = new Device(205L, 40.0, "Washing Machine", null, null);
        EnergyReport report = new EnergyReport(301L, LocalDate.of(2024, 2, 15), device, 120.5);

        assertEquals(301L, report.getEnergyReportId());
        assertEquals(LocalDate.of(2024, 2, 15), report.getDate());
        assertEquals(device, report.getDevice());
        assertEquals(120.5, report.getTotalConsumption());
    }

    @Test
    void testUpdateEnergyReport_ChangeDateAndConsumption() {
        Device device = new Device(206L, 50.0, "Oven", null, null);
        EnergyReport report = new EnergyReport(302L, LocalDate.now(), device, 0.0);
        report.setTotalConsumption(200.0);
        report.setDate(LocalDate.of(2023, 12, 1));

        assertEquals(200.0, report.getTotalConsumption());
        assertEquals(LocalDate.of(2023, 12, 1), report.getDate());
    }

    //Room
    @Test
    void testCreateRoom() {
        Device device1 = new Device(207L, 5.0, "Lamp", null, null);
        Device device2 = new Device(208L, 60.0, "Air Conditioner", null, null);
        Room room = new Room(401L, "Living Room", List.of(device1, device2));

        assertEquals(401L, room.getRoomId());
        assertEquals("Living Room", room.getName());
        assertEquals(2, room.getDevices().size());
    }

    @Test
    void testUpdateRoom_ChangeName() {
        Room room = new Room(402L, "Old Room", null);
        room.setName("Updated Room");
        assertEquals("Updated Room", room.getName());
    }

    //User
    @Test
    void testCreateUser() {
        User user = new User(501L, "Alice", "alice@example.com", "securepass");
        assertEquals(501L, user.getUserId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertTrue(user.getAutomationRules().isEmpty());
    }

    @Test
    void testUpdateUser_ChangeEmailAndPassword() {
        User user = new User(502L, "Charlie", "charlie@example.com", "password");
        user.setEmail("newcharlie@example.com");
        user.setPassword("newpassword");

        assertEquals("newcharlie@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    void testUserAutomationRules() {
        User user = new User(503L, "Bob", "bob@example.com", "12345");
        AutomationRule rule = new AutomationRule(601L, "Eco Mode", AutomationRuleType.TIME, 80.0);
        user.setAutomationRules(List.of(rule));

        assertEquals(1, user.getAutomationRules().size());
        assertEquals(rule, user.getAutomationRules().get(0));
    }
}
