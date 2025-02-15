package application.controllers;

import infrastructure.persistence.entities.*;
import infrastructure.persistence.repositories.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/database")
public class DatabaseController {

    private final AutomationRuleRepository automationRuleRepository;
    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;
    private final EnergyReportRepository energyReportRepository;
    private final UserRepository userRepository;

    public DatabaseController(
            AutomationRuleRepository automationRuleRepository,
            DeviceRepository deviceRepository,
            RoomRepository roomRepository,
            EnergyReportRepository energyReportRepository,
            UserRepository userRepository) {
        this.automationRuleRepository = automationRuleRepository;
        this.deviceRepository = deviceRepository;
        this.roomRepository = roomRepository;
        this.energyReportRepository = energyReportRepository;
        this.userRepository = userRepository;
    }

    @DeleteMapping("/delete-all")
    public String deleteAllData() {
        energyReportRepository.deleteAll();
        deviceRepository.deleteAll();
        roomRepository.deleteAll();
        automationRuleRepository.deleteAll();
        userRepository.deleteAll();

        return "Database deleted";
    }

    @PostMapping("/fill-database")
    public String fillDatabase() {

        UserEntity user1 = new UserEntity(1L, "Alice Johnson", "alice@example.com", "password1");
        UserEntity user2 = new UserEntity(2L, "Bob Smith", "bob@example.com", "password2");
        UserEntity user3 = new UserEntity(3L, "Charlie Brown", "charlie@example.com", "password3");
        UserEntity user4 = new UserEntity(4L, "Daisy Miller", "daisy@example.com", "password4");
        UserEntity user5 = new UserEntity(5L, "Ethan Hunt", "ethan@example.com", "password5");
        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));


        RoomEntity room1 = new RoomEntity(1L, "Living Room");
        RoomEntity room2 = new RoomEntity(2L, "Bedroom");
        RoomEntity room3 = new RoomEntity(3L, "Kitchen");
        RoomEntity room4 = new RoomEntity(4L, "Office");
        RoomEntity room5 = new RoomEntity(5L, "Garage");
        roomRepository.saveAll(List.of(room1, room2, room3, room4, room5));


        AutomationRuleEntity rule1 = new AutomationRuleEntity(1L, "Night Mode", domain.models.AutomationRuleType.TIME, 50.0);
        AutomationRuleEntity rule2 = new AutomationRuleEntity(2L, "Energy Saver", domain.models.AutomationRuleType.CONSUMPTION, 70.0);
        AutomationRuleEntity rule3 = new AutomationRuleEntity(3L, "Work Mode", domain.models.AutomationRuleType.TIME, 60.0);
        AutomationRuleEntity rule4 = new AutomationRuleEntity(4L, "Vacation Mode", domain.models.AutomationRuleType.TIME, 40.0);
        AutomationRuleEntity rule5 = new AutomationRuleEntity(5L, "High Power Mode", domain.models.AutomationRuleType.CONSUMPTION, 90.0);
        automationRuleRepository.saveAll(List.of(rule1, rule2, rule3, rule4, rule5));


        DeviceEntity device1 = new DeviceEntity(1L, 10.0, "Smart Light", rule1, room1);
        DeviceEntity device2 = new DeviceEntity(2L, 200.0, "Air Conditioner", rule2, room2);
        DeviceEntity device3 = new DeviceEntity(3L, 150.0, "Washing Machine", rule3, room3);
        DeviceEntity device4 = new DeviceEntity(4L, 90.0, "TV", rule4, room4);
        DeviceEntity device5 = new DeviceEntity(5L, 180.0, "Heater", rule5, room5);
        deviceRepository.saveAll(List.of(device1, device2, device3, device4, device5));


        EnergyReportEntity report1 = new EnergyReportEntity(1L, LocalDate.now().minusDays(1), device1, 12.5);
        EnergyReportEntity report2 = new EnergyReportEntity(2L, LocalDate.now().minusDays(2), device2, 25.0);
        EnergyReportEntity report3 = new EnergyReportEntity(3L, LocalDate.now().minusDays(3), device3, 40.0);
        EnergyReportEntity report4 = new EnergyReportEntity(4L, LocalDate.now().minusDays(4), device4, 35.0);
        EnergyReportEntity report5 = new EnergyReportEntity(5L, LocalDate.now().minusDays(5), device5, 50.0);
        energyReportRepository.saveAll(List.of(report1, report2, report3, report4, report5));

        return "Database filled.";
    }
}
