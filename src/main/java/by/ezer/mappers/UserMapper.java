package by.ezer.mappers;

import by.ezer.dto.UserDTO;
import by.ezer.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default")
public interface UserMapper {

    @Mapping(target = "name", source = "userName")
    UserDTO toDto(User user);
}
