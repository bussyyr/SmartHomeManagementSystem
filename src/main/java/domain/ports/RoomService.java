package domain.ports;

import domain.models.Room;
import domain.models.Device;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room createRoom(Room room);
    Room updateRoom(long roomId, Room room);
    boolean deleteRoom(long roomId);

    Optional<Room> getRoomById(long roomId);
    List<Room> getAllRooms();
    List<Device> getAllDevicesInRoom(long roomId);
}
