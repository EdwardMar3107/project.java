package by.ezer.service;

import by.ezer.dto.userDTO.UserCreateDTO;
import by.ezer.dto.userDTO.UserDTO;
import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
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
        User user = new User(
                userCreateDTO.getName(),
                userCreateDTO.getSurname(),
                userCreateDTO.getLogin(),
                userCreateDTO.getPassword(),
                userCreateDTO.getBirthDate()
        );
        userRepository.create(user);
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
    }

    public UserDTO getUserById(Long id) throws RepositoryException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RepositoryException("User with id " + id + " not found");
        }
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
    }

    public List<UserDTO> getAllUsers() throws RepositoryException {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate()))
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
        existingUser.setName(userDTO.getName());
        existingUser.setSurname(userDTO.getSurname());
        existingUser.setBirthDate(userDTO.getBirthDate());
        userRepository.update(existingUser);
    }

    public void deleteUser(Long id) throws RepositoryException {
        userRepository.delete(id);
    }
}
