package by.ezer.service;

import by.ezer.dto.UserCreateDTO;
import by.ezer.dto.UserDTO;
import by.ezer.exceptions.DatabaseException;
import by.ezer.models.User;
import by.ezer.repositories.UserRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    public UserService(Session session) {
        this.userRepository = new UserRepository(session);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) throws DatabaseException {
        if (userCreateDTO == null) {
            throw new DatabaseException("UserCreateDTO cannot be null");
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

    public UserDTO getUserById(Long id) throws DatabaseException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new DatabaseException("User with id " + id + " not found");
        }
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
    }

    public List<UserDTO> getAllUsers() throws DatabaseException {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate()))
                .collect(Collectors.toList());
    }

    public void updateUser(UserDTO userDTO) throws DatabaseException {
        if (userDTO == null || userDTO.getId() == null) {
            throw new DatabaseException("UserDTO or ID cannot be null");
        }
        User existingUser = userRepository.findById(userDTO.getId());
        if (existingUser == null) {
            throw new DatabaseException("User with ID " + userDTO.getId() + " not found");
        }
        existingUser.setName(userDTO.getName());
        existingUser.setSurname(userDTO.getSurname());
        existingUser.setBirthDate(userDTO.getBirthDate());
        userRepository.update(existingUser);
    }

    public void deleteUser(Long id) throws DatabaseException {
        userRepository.delete(id);
    }
}
