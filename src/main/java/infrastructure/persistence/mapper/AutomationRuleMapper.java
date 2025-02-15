package infrastructure.persistence.mapper;

import domain.models.AutomationRule;
import infrastructure.api.dto.AutomationRuleInput;
import infrastructure.persistence.entities.AutomationRuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DeviceIdMapper.class, DeviceMapper.class})
public interface AutomationRuleMapper {

    AutomationRule entityToDomain(AutomationRuleEntity entity);
    AutomationRuleEntity domainToEntity(AutomationRule domain);

    @Mapping(target = "devices", source = "devices", qualifiedByName = "mapDeviceIdsToDevices")
    AutomationRule inputToDomain(AutomationRuleInput input);

    @Mapping(target = "devices", source = "devices", qualifiedByName = "mapDevicesToDeviceIds")
    AutomationRuleInput domainToInput(AutomationRule domain);

    List<AutomationRule> entitiesToDomains(List<AutomationRuleEntity> entities);
    List<AutomationRuleEntity> domainsToEntities(List<AutomationRule> domains);
}
