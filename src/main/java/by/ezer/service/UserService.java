package by.ezer.service;

import by.ezer.dto.PagedResult;
import by.ezer.dto.UserDTO;
import by.ezer.entity.User;
import by.ezer.mappers.UserMapper;
import by.ezer.repositories.api.UserRepository;
import by.ezer.repositories.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(String userName, int age, String userEmail) {

        User user = new User(userName, age, userEmail);

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDTO getUserById(Long id) {

        Optional<User> userOpt = userRepository.findById(id);

        User user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user);
    }

    public PagedResult<UserDTO> getAllPaged(int page, int size) {

        PagedResult<User> result = userRepository.findAllPaged(page, size);

        List<UserDTO> dtos = result.getContent().stream()
                .map(userMapper::toDto)
                .toList();

        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
    }

    public List<UserDTO> findUsersByName(String userName) {

        Optional<User> userOpt = userRepository.findByName(userName);

        return userOpt.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDTO> findUsersByEmail(String UserEmail) {

        Optional<User> users = userRepository.findByEmail(UserEmail);

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void updateUser(Long userId, UserDTO userDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(userDto.name());
        user.setAge(userDto.age());
        user.setEmail(userDto.email());

        userRepository.update(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
