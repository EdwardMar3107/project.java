package by.ezer.security.auth;

import by.ezer.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Это REST API контроллер. Возвращает JSON и принимает HTTP запросы
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    // Главный объект Spring Security.
    private final AuthenticationManager authManager; // проверяет логин, проверяет логин, вызывает UserDetailsService, вызывает PasswordEncoder
    // Мой сервис:
    private final JwtService jwtService; // создаёт JWT и создаёт JWT. Сервис для генерации JWT токена

    @PostMapping("/login") // Обрабатывает POST запросы на /auth/login
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) { // Принимаем JSON с email и password и превращаем его в объект LoginRequest

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

         /*
            Передаём email и пароль в Spring Security.
            AuthenticationManager:
            1) вызывает CustomUserDetailsService
            2) загружает пользователя из БД
            3) сравнивает пароль через PasswordEncoder
            4) если всё правильно — возвращает Authentication объект
            5) если нет — выбрасывает ошибку (401)
        */

        UserDetails user =
                (UserDetails) authentication.getPrincipal();  // Получаем пользователя, который успешно прошёл аутентификацию

        String token =
                jwtService.generateToken(user); // Генерируем JWT токен для этого пользователя

        return ResponseEntity.ok(
                new LoginResponse(token) // Возвращаем HTTP 200 и JSON с токеном клиенту
        );
    }
}
