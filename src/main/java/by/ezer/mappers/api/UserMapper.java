package by.ezer.mappers.api;

import by.ezer.dto.UserDTO;
import by.ezer.entity.User;

public interface UserMapper {
    UserDTO toDto(User user);
}
