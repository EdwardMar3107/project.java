package by.ezer.security;

import by.ezer.entity.User;
import by.ezer.repositories.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// Мы реализуем интерфейс Spring Security: "Я умею загружать пользователей". Spring Security ИЩЕТ этот интерфейс автоматически.
public class CustomUserDetailsService implements UserDetailsService {

    // Используется чтобы: найти пользователя по email
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(  // Преобразуем User (Entity) → UserDetails (Security)
                user.getEmail(), // Указываем username для Security:
                user.getPassword(), // пароль из БД (уже BCrypt)
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())) // Формируем список ролей.
        );
    }
}
