package infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices")
public class DeviceEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deviceId;

    private String name;

    private boolean status;

    private double totalConsumptionPerHour;

    @OneToMany
    private List<EnergyReportEntity> energyReportEntities;

    @OneToOne
    private AutomationRuleEntity automationRule;

    @ManyToOne
    private RoomEntity room;

    public DeviceEntity() {

    }

    public DeviceEntity(long deviceId, double totalConsumptionPerHour, String name, AutomationRuleEntity automationRule, RoomEntity room) {
        this.deviceId = deviceId;
        this.name = name;
        this.status = false;
        this.totalConsumptionPerHour = totalConsumptionPerHour;
        this.room = room;
        this.energyReportEntities = new ArrayList<>();
        this.automationRule = automationRule;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotalConsumptionPerHour() {
        return totalConsumptionPerHour;
    }

    public void setTotalConsumptionPerHour(double totalConsumptionPerHour) {
        this.totalConsumptionPerHour = totalConsumptionPerHour;
    }

    public List<EnergyReportEntity> getEnergyReports() {
        return energyReportEntities;
    }

    public void setEnergyReports(List<EnergyReportEntity> energyReportEntities) {
        this.energyReportEntities = energyReportEntities;
    }

    public AutomationRuleEntity getAutomationRule() {
        return automationRule;
    }

    public void setAutomationRule(AutomationRuleEntity automationRule) {
        this.automationRule = automationRule;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }
}

