package infrastructure.persistence.adapters;

import domain.models.EnergyReport;
import domain.ports.EnergyReportService;
import infrastructure.persistence.entities.EnergyReportEntity;
import infrastructure.persistence.repositories.EnergyReportRepository;
import jakarta.persistence.EntityNotFoundException;
import infrastructure.persistence.mapper.EnergyReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnergyReportServiceImpl implements EnergyReportService {
    private final EnergyReportRepository energyReportRepository;
    private final EnergyReportMapper energyReportMapper;

    @Autowired
    public EnergyReportServiceImpl(EnergyReportRepository energyReportRepository, EnergyReportMapper energyReportMapper) {
        this.energyReportRepository = energyReportRepository;
        this.energyReportMapper = energyReportMapper;
    }

    public EnergyReport createEnergyReport(EnergyReport energyReport) {
        EnergyReportEntity entity = energyReportMapper.domainToEntity(energyReport);
        EnergyReportEntity savedEntity = energyReportRepository.save(entity);
        return energyReportMapper.entityToDomain(savedEntity);
    }

    public boolean deleteEnergyReport(long id) {
        if(energyReportRepository.existsById(id)){
            energyReportRepository.deleteById(id);
            return true;
        }else{
            throw new EntityNotFoundException("Energy Report with id " + id + " not found!");
        }
    }

    public Optional<EnergyReport> getEnergyReportById(long id) {
        return energyReportRepository.findById(id)
                .map(energyReportMapper::entityToDomain);
    }

    public Optional<EnergyReport> getEnergyReportByDate(Date date) {
        return energyReportRepository.findByDate(date)
                .map(energyReportMapper::entityToDomain);
    }

    public List<EnergyReport> getAllEnergyReports() {
        return energyReportRepository.findAll()
                .stream()
                .map(energyReportMapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
