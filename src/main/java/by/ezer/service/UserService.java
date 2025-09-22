package by.ezer.service;

import by.ezer.dto.userDTO.UserCreateDTO;
import by.ezer.dto.userDTO.UserDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.UserMapper;
import by.ezer.models.User;
import by.ezer.repositories.api.UserRepository;
import by.ezer.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO save(UserCreateDTO userCreateDTO) throws RepositoryException {

        User user = UserMapper.INSTANCE.toEntity(userCreateDTO);
        userRepository.create(user);
        return UserMapper.INSTANCE.toDTO(user);
    }

    public UserDTO findById(Long id) throws RepositoryException {

        User user = userRepository.findById(id);
        if (user == null) {
            throw new RepositoryException("User not found");
        }
        return UserMapper.INSTANCE.toDTO(user);
    }

    public List<UserDTO> findAll() throws RepositoryException {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public void update(UserDTO userDTO) throws RepositoryException {

        User existingUser = userRepository.findById(userDTO.getId());
        if (existingUser == null) {
            throw new RepositoryException("User with id " + userDTO.getId() + " does not exist");
        }

        UserMapper.INSTANCE.updateUserFromDTO(userDTO, existingUser);
        userRepository.update(existingUser);
    }

    public void delete(Long id) throws RepositoryException {
        userRepository.delete(id);
    }
}
