package by.ezer.service;

import by.ezer.dto.userDTO.UserCreateDTO;
import by.ezer.dto.userDTO.UserDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.UserMapper;
import by.ezer.models.User;
import by.ezer.repositories.api.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO createUser(UserCreateDTO userCreateDTO) throws RepositoryException {
        if (userCreateDTO == null) {
            log.error("UserCreateDTO is null");
            throw new RepositoryException("UserCreateDTO cannot be null");
        }
        if (userCreateDTO.getName() == null || userCreateDTO.getBirthDate() == null) {
            log.warn("Name or birth date is missing");
            throw new RepositoryException("Name and birth date are required");
        }

        User user = UserMapper.INSTANCE.toEntity(userCreateDTO);
        userRepository.create(user);
        log.info("User created");
        return UserMapper.INSTANCE.toDTO(user);
    }

    public UserDTO getUserById(Long id) throws RepositoryException {
        User user = userRepository.findById(id);
        if (user == null) {
            handleNotFound(id);
        }
        return UserMapper.INSTANCE.toDTO(user);
    }

    public List<UserDTO> getAllUsers() throws RepositoryException {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public void updateUser(UserDTO userDTO) throws RepositoryException {
        if (userDTO == null || userDTO.getId() == null) {
            log.error("UserDTO is null");
            throw new RepositoryException("UserDTO or ID cannot be null");
        }
        User existingUser = userRepository.findById(userDTO.getId());
        if (existingUser == null) {
            handleNotFound(userDTO.getId());
        }

        UserMapper.INSTANCE.updateUserFromDTO(userDTO, existingUser);
        log.info("User updated");
        userRepository.update(existingUser);
    }

    public void deleteUser(Long id) throws RepositoryException {
        userRepository.delete(id);
        log.info("User deleted");
    }

    private void handleNotFound(Long id) throws RepositoryException {
        String errorMessage = "User with id " + id + " not found";
        log.error(errorMessage);
        throw new RepositoryException(errorMessage);
    }
}
