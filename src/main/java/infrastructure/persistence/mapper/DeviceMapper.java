package infrastructure.persistence.mapper;

import infrastructure.api.dto.DeviceInput;
import domain.models.Device;
import infrastructure.persistence.entities.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    Device entityToDomain(DeviceEntity entity);
    DeviceEntity domainToEntity(Device domain);

    Device inputToDomain(DeviceInput input);
    DeviceInput domainToInput(Device domain);

    List<Device> entitiesToDomains(List<DeviceEntity> entities);
    List<DeviceEntity> domainsToEntities(List<Device> domains);
}
