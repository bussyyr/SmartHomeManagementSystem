package infrastructure.persistence.adapters;

import domain.models.Device;
import domain.ports.DeviceService;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.entities.RoomEntity;
import infrastructure.persistence.mapper.AutomationRuleMapper;
import infrastructure.persistence.mapper.DeviceMapper;
import infrastructure.persistence.mapper.RoomMapper;
import infrastructure.persistence.repositories.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper = DeviceMapper.INSTANCE;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;
    private final AutomationRuleMapper automationRuleMapper;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, AutomationRuleMapper automationRuleMapper) {
        this.deviceRepository = deviceRepository;
        this.automationRuleMapper = automationRuleMapper;
    }

    public Device createDevice(Device device) {
        DeviceEntity entity = deviceMapper.domainToEntity(device);
        DeviceEntity savedEntity = deviceRepository.save(entity);
        return deviceMapper.entityToDomain(savedEntity);
    }

    public Device updateDevice(long id, Device device) {
        DeviceEntity existingEntity = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device with id " + id + " not found!"));

        RoomEntity roomEntity = roomMapper.domainToEntity(device.getRoom());
        existingEntity.setRoom(roomEntity);
        existingEntity.setName(device.getName());
        existingEntity.setTotalConsumptionPerHour(device.getTotalConsumptionPerHour());

        DeviceEntity updatedEntity = deviceRepository.save(existingEntity);
        return deviceMapper.entityToDomain(updatedEntity);
    }

    public boolean deleteDevice(long id) {
        if(deviceRepository.existsById(id)){
            deviceRepository.deleteById(id);
            return true;
        }else{
            throw new EntityNotFoundException("Device with id " + id + " not found!");
        }
    }

    public Optional<Device> getDeviceById(long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::entityToDomain);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::entityToDomain)
                .collect(Collectors.toList());
    }
}
