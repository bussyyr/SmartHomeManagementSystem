package infrastructure.persistence.entities;

import domain.models.AutomationRuleType;
import domain.models.Device;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "automation_rule")
public class AutomationRuleEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long automationRuleId;

    private String name;

    @Enumerated(EnumType.STRING)
    private AutomationRuleType ruleType;

    private Double consumptionLimit;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "automationRuleId")
    private List<DeviceEntity> devices;


    public AutomationRuleEntity() {}

    public AutomationRuleEntity(long automationRuleId, String name, AutomationRuleType ruleType, Double consumptionLimit) {
        this.automationRuleId = automationRuleId;
        this.name = name;
        this.ruleType = ruleType;
        this.consumptionLimit = consumptionLimit;
        this.devices = new ArrayList<>();
    }

    public long getAutomationRuleId() {
        return automationRuleId;
    }

    public void setAutomationRuleId(long automationRuleId) {
        this.automationRuleId = automationRuleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AutomationRuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(AutomationRuleType ruleType) {
        this.ruleType = ruleType;
    }

    public Double getConsumptionLimit() {
        return consumptionLimit;
    }

    public void setConsumptionLimit(Double consumptionLimit) {
        this.consumptionLimit = consumptionLimit;
    }

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
    }
}
