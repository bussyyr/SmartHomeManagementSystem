package domain.models;

import java.time.LocalDate;

public class EnergyReport {
    private long energyReportId;
    private LocalDate date;
    private Device device;
    private double totalConsumption;

    public EnergyReport(long energyReportId, LocalDate date, Device device, double totalConsumption) {
        this.energyReportId = energyReportId;
        this.date = date;
        this.device = device;
        this.totalConsumption = totalConsumption;
    }

    public double dailyTotalConsumption(){
        return device.getTotalConsumptionPerHour() * device.getDailyConsumption();
    }

    public long getEnergyReportId() {
        return energyReportId;
    }

    public void setEnergyReportId(long energyReportId) {
        this.energyReportId = energyReportId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
