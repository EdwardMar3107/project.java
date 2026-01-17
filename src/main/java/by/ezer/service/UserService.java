package by.ezer.service;

import by.ezer.dto.PagedResult;
import by.ezer.dto.UserCreateDTO;
import by.ezer.dto.UserDTO;
import by.ezer.entity.User;
import by.ezer.mappers.UserMapper;
import by.ezer.repositories.api.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserCreateDTO request) {
        User user = new User(request.userName(), request.age(), request.email());

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public PagedResult<UserDTO> findAllPaged(int page, int size) {
        PagedResult<User> result = userRepository.findAllPaged(page, size);

        List<UserDTO> dtos = result.getContent().stream()
                .map(userMapper::toDto)
                .toList();

        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
    }

    @Transactional
    public void updateUser(Long userId, UserDTO userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(userDto.userName());
        user.setAge(userDto.age());
        user.setEmail(userDto.email());

        userRepository.update(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
