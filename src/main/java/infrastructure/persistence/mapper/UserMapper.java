package infrastructure.persistence.mapper;

import infrastructure.api.dto.UserInput;
import domain.models.User;
import infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User entityToDomain(UserEntity entity);
    UserEntity domainToEntity(User domain);

    User inputToDomain(UserInput input);
    UserInput domainToInput(User domain);

    List<User> entitiesToDomains(List<UserEntity> entities);
    List<UserEntity> domainsToEntities(List<User> domains);
}
