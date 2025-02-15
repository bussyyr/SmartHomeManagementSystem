package application.controllers;

import infrastructure.api.dto.AutomationRuleInput;
import domain.models.AutomationRule;
import infrastructure.persistence.adapters.AutomationRuleServiceImpl;
import infrastructure.persistence.mapper.AutomationRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation_rules")
public class AutomationRuleController {

    private final AutomationRuleServiceImpl automationRuleServiceImpl;
    private AutomationRuleMapper automationRuleMapper;

    @Autowired
    public AutomationRuleController(AutomationRuleServiceImpl automationRuleServiceImpl, AutomationRuleMapper automationRuleMapper) {
        this.automationRuleServiceImpl = automationRuleServiceImpl;
        this.automationRuleMapper = automationRuleMapper;
    }

    @GetMapping
    public List<AutomationRule> getAllAutomationRules() {
        return automationRuleServiceImpl.getAllAutomationRules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomationRule> getAutomationRuleById(@PathVariable long id) {
        return automationRuleServiceImpl.getAutomationRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AutomationRule> createAutomationRule(@RequestBody AutomationRuleInput automationRuleInput) {
        AutomationRule automationRule = automationRuleMapper.inputToDomain(automationRuleInput);
        AutomationRule createdRule = automationRuleServiceImpl.createAutomationRule(automationRule);
        return ResponseEntity.status(201).body(createdRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutomationRule> updateAutomationRule(@PathVariable long id, @RequestBody AutomationRuleInput automationRuleInput) {
        AutomationRule automationRule = automationRuleMapper.inputToDomain(automationRuleInput);
        AutomationRule updatedRule = automationRuleServiceImpl.updateAutomationRule(id, automationRule);
        if (updatedRule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutomationRule(@PathVariable long id) {
        boolean deleted = automationRuleServiceImpl.deleteAutomationRule(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}