package infrastructure.persistence.mapper;

import domain.models.Device;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.mapper.DeviceMapper;
import infrastructure.persistence.repositories.DeviceRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DeviceIdMapper {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceIdMapper(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Named("mapDeviceIdsToDevices")
    public List<Device> mapDeviceIdsToDevices(List<Long> deviceIds) {
        if (deviceIds == null || deviceIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<DeviceEntity> deviceEntities = deviceRepository.findAllById(deviceIds);
        return deviceMapper.entitiesToDomains(deviceEntities);
    }

    @Named("mapDevicesToDeviceIds")
    public List<Long> mapDevicesToDeviceIds(List<Device> devices) {
        if (devices == null || devices.isEmpty()) {
            return Collections.emptyList();
        }
        return devices.stream().map(Device::getDeviceId).toList();
    }

    @Named("mapDeviceIdtoDevice")
    public Device mapDeviceIdtoDevice(Long deviceId) {
        if (deviceId == null) {
            return null;
        }
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElseGet(null);
        return deviceMapper.entityToDomain(deviceEntity);
    }

    @Named("mapDevicetoDeviceId")
    public Long mapDevicetoDeviceId(Device device) {
        if (device == null) {
            return null;
        }
        DeviceEntity deviceEntity = deviceRepository.findById(device.getDeviceId()).orElseGet(null);
        return deviceEntity.getDeviceId();
    }
}
