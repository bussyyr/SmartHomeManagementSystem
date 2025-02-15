package infrastructure.persistence.mapper;

import infrastructure.api.dto.RoomInput;
import domain.models.Room;
import infrastructure.persistence.entities.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room entityToDomain(RoomEntity entity);
    RoomEntity domainToEntity(Room domain);

    Room inputToDomain(RoomInput input);
    RoomInput domainToInput(Room domain);

    List<Room> entitiesToDomains(List<RoomEntity> entities);
    List<RoomEntity> domainsToEntities(List<Room> domains);
}
