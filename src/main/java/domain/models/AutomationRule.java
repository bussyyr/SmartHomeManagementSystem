package domain.models;

import java.util.ArrayList;
import java.util.List;

public class AutomationRule {
    private long automationRuleId;
    private String name;
    private AutomationRuleType ruleType;
    private List<Device> devices;
    private Double consumptionLimit;

    public AutomationRule() {
    }

    public AutomationRule(long automationRuleId, String name, AutomationRuleType ruleType, Double consumptionLimit) {
        this.automationRuleId = automationRuleId;
        this.name = name;
        this.ruleType = ruleType;
        this.consumptionLimit = consumptionLimit;
        this.devices = new ArrayList<>();
    }

    public void automate(boolean isNight, double deviceConsumption) {
        boolean shouldExecute = false;

        switch (ruleType) {
            case TIME:
                if (isNight) {
                    shouldExecute = true;
                }
                break;
            case CONSUMPTION:
                if (consumptionLimit != null && deviceConsumption > consumptionLimit) {
                    shouldExecute = true;
                }
                break;
        }

        if (shouldExecute) {
            for (Device device : devices) {
                device.turnOff();
            }
        }
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Double getConsumptionLimit() {
        return consumptionLimit;
    }

    public void setConsumptionLimit(Double consumptionLimit) {
        this.consumptionLimit = consumptionLimit;
    }
}
