package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.EnergyReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface EnergyReportRepository extends JpaRepository<EnergyReportEntity, Long> {
    Optional<EnergyReportEntity> findByDate(Date date);
}
