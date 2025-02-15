package domain.models;

import infrastructure.persistence.entities.AutomationRuleEntity;
import infrastructure.persistence.entities.RoomEntity;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Device {

    private long deviceId;
    private String name;
    private boolean status;
    private double totalConsumptionPerHour;
    private LocalTime lastTurnOnTime;
    private double dailyConsumption;
    private List<EnergyReport> energyReports;
    private AutomationRule automationRule;
    private Room room;

    public Device() {
    }


    public Device(long deviceId, double totalConsumptionPerHour, String name, AutomationRule automationRule, Room room) {
        this.deviceId = deviceId;
        this.name = name;
        this.status = false;
        this.totalConsumptionPerHour = totalConsumptionPerHour;
        this.lastTurnOnTime = null;
        this.dailyConsumption = totalConsumptionPerHour * 24;
        this.energyReports = new ArrayList<>();
        this.automationRule = automationRule;
        this.room = room;
    }

    public void turnOn(){
        this.status = true;
        this.lastTurnOnTime = LocalTime.now();
    }

    public void turnOff(){
        this.status = false;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
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

    public LocalTime getLastTurnOnTime() {
        return lastTurnOnTime;
    }

    public void setLastTurnOnTime(LocalTime lastTurnOnTime) {
        this.lastTurnOnTime = lastTurnOnTime;
    }

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public List<EnergyReport> getEnergyReports() {
        return energyReports;
    }

    public void setEnergyReports(List<EnergyReport> energyReports) {
        this.energyReports = energyReports;
    }

    public AutomationRule getAutomationRule() {
        return automationRule;
    }

    public void setAutomationRule(AutomationRule automationRule) {
        this.automationRule = automationRule;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
