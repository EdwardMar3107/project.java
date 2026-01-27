package by.ezer.mappers;

import by.ezer.dto.UserDTO;
import by.ezer.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
}
