package infrastructure.persistence.mapper;

import infrastructure.api.dto.EnergyReportInput;
import domain.models.EnergyReport;
import infrastructure.persistence.entities.EnergyReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {DeviceIdMapper.class})
public interface EnergyReportMapper {
    EnergyReportMapper INSTANCE = Mappers.getMapper(EnergyReportMapper.class);

    EnergyReport entityToDomain(EnergyReportEntity entity);
    EnergyReportEntity domainToEntity(EnergyReport domain);

    @Mapping(target = "device", source = "device", qualifiedByName = "mapDeviceIdtoDevice")
    EnergyReport inputToDomain(EnergyReportInput input);
    @Mapping(target = "device", source = "device", qualifiedByName = "mapDevicetoDeviceId")
    EnergyReportInput domainToInput(EnergyReport domain);
}
