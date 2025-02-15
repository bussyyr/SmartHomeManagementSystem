package infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String name;

    private String email;

    private String password;

    @OneToMany
    private List<AutomationRuleEntity> automationRules;

    public UserEntity() {

    }

    public UserEntity(long userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.automationRules = new ArrayList<>();
    }

    public void addAutomationRule(AutomationRuleEntity automationRule){
        automationRules.add(automationRule);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AutomationRuleEntity> getAutomationRules() {
        return automationRules;
    }

    public void setAutomationRules(List<AutomationRuleEntity> automationRules) {
        this.automationRules = automationRules;
    }
}
