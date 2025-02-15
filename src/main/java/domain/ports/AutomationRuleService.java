package domain.ports;

import domain.models.AutomationRule;
import java.util.List;
import java.util.Optional;

public interface AutomationRuleService {
    AutomationRule createAutomationRule(AutomationRule automationRule);
    AutomationRule updateAutomationRule(long id, AutomationRule automationRule);
    boolean deleteAutomationRule(long id);
    Optional<AutomationRule> getAutomationRuleById(long id);
    List<AutomationRule> getAllAutomationRules();
}
