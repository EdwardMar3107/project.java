package by.ezer.service;

import by.ezer.dto.userDTO.UserCreateDTO;
import by.ezer.dto.userDTO.UserDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.UserMapper;
import by.ezer.models.User;
import by.ezer.repositories.api.UserRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    public UserService(Session session) {
        this.userRepository = new by.ezer.repositories.impl.UserRepositoryImpl(session);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) throws RepositoryException {
        if (userCreateDTO == null) {
            throw new RepositoryException("UserCreateDTO cannot be null");
        }

        User user = UserMapper.INSTANCE.toEntity(userCreateDTO);

        userRepository.create(user);
        return UserMapper.INSTANCE.toDTO(user);
    }

    public UserDTO getUserById(Long id) throws RepositoryException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RepositoryException("User with id " + id + " not found");
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
            throw new RepositoryException("UserDTO or ID cannot be null");
        }
        User existingUser = userRepository.findById(userDTO.getId());
        if (existingUser == null) {
            throw new RepositoryException("User with ID " + userDTO.getId() + " not found");
        }

        UserMapper.INSTANCE.updateUserFromDTO(userDTO, existingUser);

        userRepository.update(existingUser);
    }

    public void deleteUser(Long id) throws RepositoryException {
        userRepository.delete(id);
    }
}
