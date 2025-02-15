package infrastructure.persistence.adapters;

import domain.models.Room;
import domain.models.Device;
import domain.ports.RoomService;
import infrastructure.persistence.entities.DeviceEntity;
import infrastructure.persistence.entities.RoomEntity;
import infrastructure.persistence.repositories.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import infrastructure.persistence.mapper.DeviceMapper;
import infrastructure.persistence.mapper.RoomMapper;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;
    private final DeviceMapper deviceMapper = DeviceMapper.INSTANCE;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public Room createRoom(Room room){
        RoomEntity entity = roomMapper.domainToEntity(room);
        RoomEntity savedEntity = roomRepository.save(entity);
        return roomMapper.entityToDomain(savedEntity);
    }

    public Room updateRoom(long id, Room room){
        RoomEntity existingEntity = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + id + " not found!"));

        existingEntity.setName(room.getName());
        List<DeviceEntity> deviceEntities = deviceMapper.domainsToEntities(room.getDevices());
        existingEntity.setDevices(deviceEntities);

        RoomEntity updatedEntity = roomRepository.save(existingEntity);
        return roomMapper.entityToDomain(updatedEntity);
    }

    public boolean deleteRoom(long id){
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        } else {
            throw new EntityNotFoundException("Room with id " + id + " not found!");
        }
    }

    public Optional<Room> getRoomById(long id) {
        return roomRepository.findById(id)
                .map(roomMapper::entityToDomain);
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    public List<Device> getAllDevicesInRoom(long id) {
        RoomEntity existingEntity = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + id + " not found!"));
        Room existingRoom = roomMapper.entityToDomain(existingEntity);
        List<DeviceEntity> deviceEntities = deviceMapper.domainsToEntities(existingRoom.getDevices()); // devices burada Room objesinin içinde yer almalı
        if (deviceEntities == null || deviceEntities.isEmpty()) {
            throw new EntityNotFoundException("No devices found for room with id " + id);
        }
        return deviceMapper.entitiesToDomains(deviceEntities);
    }
}
