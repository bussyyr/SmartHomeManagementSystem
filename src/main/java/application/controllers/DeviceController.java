package application.controllers;

import domain.models.Device;
import infrastructure.api.dto.DeviceInput;
import infrastructure.persistence.adapters.DeviceServiceImpl;
import infrastructure.persistence.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    private final DeviceServiceImpl deviceServiceImpl;
    private final DeviceMapper deviceMapper = DeviceMapper.INSTANCE;

    @Autowired
    public DeviceController(DeviceServiceImpl deviceServiceImpl) {
        this.deviceServiceImpl = deviceServiceImpl;
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceServiceImpl.getAllDevices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable long id) {
        return deviceServiceImpl.getDeviceById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceInput deviceInput) {
        Device device = deviceMapper.inputToDomain(deviceInput);
        Device createdDevice = deviceServiceImpl.createDevice(device);
        return ResponseEntity.status(201).body(createdDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable long id, @RequestBody DeviceInput deviceInput) {
        Device device = deviceMapper.inputToDomain(deviceInput);
        Device updatedDevice = deviceServiceImpl.updateDevice(id, device);
        if(updatedDevice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable long id) {
        boolean deleted = deviceServiceImpl.deleteDevice(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
