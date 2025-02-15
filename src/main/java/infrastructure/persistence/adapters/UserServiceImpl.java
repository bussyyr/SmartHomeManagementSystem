package infrastructure.persistence.adapters;

import domain.models.User;
import domain.ports.UserService;
import infrastructure.persistence.entities.AutomationRuleEntity;
import infrastructure.persistence.entities.UserEntity;
import infrastructure.persistence.mapper.AutomationRuleMapper;
import infrastructure.persistence.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import infrastructure.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final AutomationRuleMapper automationRuleMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AutomationRuleMapper automationRuleMapper){
        this.userRepository = userRepository;
        this.automationRuleMapper = automationRuleMapper;
    }

    public User createUser(User user){
        UserEntity entity = userMapper.domainToEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToDomain(savedEntity);
    }

    public User updateUser(long id, User user){
        UserEntity existingEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"));
        existingEntity.setName(user.getName());
        existingEntity.setEmail(user.getEmail());
        existingEntity.setPassword(user.getPassword());

        List<AutomationRuleEntity> automationRuleEntities = automationRuleMapper.domainsToEntities(user.getAutomationRules());
        existingEntity.setAutomationRules(automationRuleEntities);

        UserEntity updatedEntity = userRepository.save(existingEntity);
        return userMapper.entityToDomain(updatedEntity);
    }

    public boolean deleteUser(long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else{
            throw new EntityNotFoundException("User with id " + id + " not found!");
        }
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id)
                .map(userMapper::entityToDomain);
    }


    public Optional<User>  getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .map(userMapper::entityToDomain);
    }

    public List<User> getUserByName(String name) {
        List<UserEntity> users = userRepository.findByName(name);
        return userMapper.entitiesToDomains(users);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
