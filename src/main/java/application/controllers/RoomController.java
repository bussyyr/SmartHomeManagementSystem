package application.controllers;


import domain.models.Room;
import domain.models.Device;
import infrastructure.api.dto.RoomInput;
import infrastructure.persistence.adapters.RoomServiceImpl;
import infrastructure.persistence.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;

    @Autowired
    public RoomController(RoomServiceImpl roomServiceImpl) {
        this.roomServiceImpl = roomServiceImpl;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomServiceImpl.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable long id) {
        return roomServiceImpl.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /// ///bu yok diye olabilir
    @GetMapping("/{id}/devices")
    public ResponseEntity<List<Device>> getAllDevicesInRoom(@PathVariable Long id) {
        List<Device> devices = roomServiceImpl.getAllDevicesInRoom(id);
        if (devices == null || devices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(devices);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomInput roomInput) {
        Room room = roomMapper.inputToDomain(roomInput);
        Room createdRoom = roomServiceImpl.createRoom(room);
        return ResponseEntity.status(201).body(createdRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody RoomInput roomInput) {
        Room room = roomMapper.inputToDomain(roomInput);
        Room updatedRoom = roomServiceImpl.updateRoom(id, room);
        if(updatedRoom == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        boolean deleted = roomServiceImpl.deleteRoom(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
