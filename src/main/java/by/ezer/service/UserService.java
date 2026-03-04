package by.ezer.service;

import by.ezer.aspect.annotation.Cacheable;
import by.ezer.aspect.annotation.Loggable;
import by.ezer.dto.UserCreateDTO;
import by.ezer.dto.UserDTO;
import by.ezer.entity.Role;
import by.ezer.entity.User;
import by.ezer.exceptions.ServiceException;
import by.ezer.mappers.UserMapper;
import by.ezer.repositories.api.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Cacheable
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Loggable
    public UserDTO createUser(UserCreateDTO request) {
        try {
            String encodedPassword = passwordEncoder.encode(request.password());

            User user = new User(request.name(), request.age(), request.email(), encodedPassword);

            userRepository.save(user);

            return userMapper.toDto(user);

        } catch (Exception e) {
            throw new ServiceException("Cannot save user in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Loggable
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }
//
//    public PagedResult<UserDTO> findAllPaged(int page, int size) {
//        PagedResult<User> result = userRepository.findAllPaged(page, size);
//
//        List<UserDTO> dtos = result.getContent().stream()
//                .map(userMapper::toDto)
//                .toList();
//
//        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
//    }
//
//    @Transactional
//    public void updateUser(Long userId, UserDTO userDto) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setUserName(userDto.userName());
//        user.setAge(userDto.age());
//        user.setEmail(userDto.email());
//
//        //userRepository.update(user);
//    }
//
    @Transactional
    @Loggable
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
