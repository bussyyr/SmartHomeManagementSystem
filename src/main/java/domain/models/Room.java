package domain.models;

import java.util.List;

public class Room {
    private long roomId;
    private String name;
    private List<Device> devices;

    public Room() {
    }

    public Room(long roomId, String name, List<Device> devices) {
        this.roomId = roomId;
        this.name = name;
        this.devices = devices;
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
