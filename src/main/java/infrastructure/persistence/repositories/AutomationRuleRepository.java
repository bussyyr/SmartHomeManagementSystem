package infrastructure.persistence.repositories;

import infrastructure.persistence.entities.AutomationRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationRuleRepository extends JpaRepository<AutomationRuleEntity, Long> {
}
