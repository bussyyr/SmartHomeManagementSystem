package domain.ports;

import domain.models.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    Device createDevice(Device device);
    Device updateDevice(long id, Device device);
    boolean deleteDevice(long id);
    Optional<Device> getDeviceById(long id);
    List<Device> getAllDevices();
}
