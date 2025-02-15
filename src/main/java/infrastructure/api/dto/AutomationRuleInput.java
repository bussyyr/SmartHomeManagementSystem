package infrastructure.api.dto;

import domain.models.AutomationRuleType;
import java.util.List;

public class AutomationRuleInput {
    private String name;
    private AutomationRuleType ruleType;
    private List<Long> devices;
    private Double consumptionLimit;

    public AutomationRuleInput() {}

    public AutomationRuleInput(String name, AutomationRuleType ruleType, List<Long> devices, Double consumptionLimit) {
        this.name = name;
        this.ruleType = ruleType;
        this.devices = devices;
        this.consumptionLimit = consumptionLimit;
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

    public List<Long> getDevices() {
        return devices;
    }

    public void setDevices(List<Long> devices) {
        this.devices = devices;
    }

    public Double getConsumptionLimit() {
        return consumptionLimit;
    }

    public void setConsumptionLimit(Double consumptionLimit) {
        this.consumptionLimit = consumptionLimit;
    }
}
