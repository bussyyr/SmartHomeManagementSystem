package application.controllers;

import domain.models.EnergyReport;
import infrastructure.api.dto.EnergyReportInput;
import infrastructure.persistence.adapters.EnergyReportServiceImpl;
import infrastructure.persistence.mapper.EnergyReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/energy_reports")
public class EnergyReportController {
    private final EnergyReportServiceImpl energyReportServiceImpl;
    private final EnergyReportMapper energyReportMapper;

    @Autowired
    public EnergyReportController(EnergyReportServiceImpl energyReportServiceImpl, EnergyReportMapper energyReportMapper) {
        this.energyReportServiceImpl = energyReportServiceImpl;
        this.energyReportMapper = energyReportMapper;
    }

    @GetMapping
    public List<EnergyReport> getAllEnergyReports(){
        return energyReportServiceImpl.getAllEnergyReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnergyReport> getEnergyReportById(@PathVariable long id) {
        return energyReportServiceImpl.getEnergyReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<EnergyReport> getEnergyReportByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return energyReportServiceImpl.getEnergyReportByDate(date)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EnergyReport> createEnergyReport(@RequestBody EnergyReportInput energyReportInput){
        EnergyReport energyReport = energyReportMapper.inputToDomain(energyReportInput);
        EnergyReport createdReport = energyReportServiceImpl.createEnergyReport(energyReport);
        return ResponseEntity.status(201).body(createdReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnergyReport(@PathVariable long id){
        boolean deleted = energyReportServiceImpl.deleteEnergyReport(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
