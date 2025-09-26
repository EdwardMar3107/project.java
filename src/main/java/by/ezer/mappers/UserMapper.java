package by.ezer.mappers;

import by.ezer.dto.userDTO.UserCreateDTO;
import by.ezer.dto.userDTO.UserDTO;
import by.ezer.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "login", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDTO(UserDTO dto, @MappingTarget User user);
}
