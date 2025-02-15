package infrastructure.api.dto;

import java.time.LocalDate;
import java.util.Date;

public class EnergyReportInput {
    private Long device;
    private LocalDate date;
    private double totalConsumption;

    public EnergyReportInput() {}

    public EnergyReportInput(Long device, double totalConsumption) {
        this.device = device;
        this.totalConsumption = totalConsumption;
        this.date = LocalDate.now();
    }

    public Long getDevice() {
        return device;
    }

    public void setDevice(Long device) {
        this.device = device;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
