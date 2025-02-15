package infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class RoomEntity {

    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @Column
    private String name;

    @OneToMany
    private List<DeviceEntity> deviceEntities;

    public RoomEntity(){

    }

    public RoomEntity(long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
        this.deviceEntities = new ArrayList<>();
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DeviceEntity> getDevices() {
        return deviceEntities;
    }

    public void setDevices(List<DeviceEntity> deviceEntities) {
        this.deviceEntities = deviceEntities;
    }
}
