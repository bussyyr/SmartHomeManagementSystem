package infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "energy_reports")
public class EnergyReportEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long energyReportId;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ManyToOne
    private DeviceEntity device;

    @Column
    private double totalConsumption;

    public EnergyReportEntity() {

    }

    public EnergyReportEntity(long energyReportId, LocalDate date, DeviceEntity device, double totalConsumption) {
        this.energyReportId = energyReportId;
        this.date = date;
        this.device = device;
        this.totalConsumption = totalConsumption;
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

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
