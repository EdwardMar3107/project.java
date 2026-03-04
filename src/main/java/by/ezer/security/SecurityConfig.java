package by.ezer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter; //кастомный фильтр JWT
    private final CustomUserDetailsService userDetailsService; // сервис загрузки пользователей из БД

    @Bean  // Spring: создать объект и управлять им
    public SecurityFilterChain filterChain(HttpSecurity http) // HttpSecurity — объект-конфигуратор всей security логики
            throws Exception {

        http // Начинаем fluent-конфигурацию
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF защиту, JWT + REST API = Stateless.
                                                       // CSRF нужен только для cookie-based auth.
                .sessionManagement(session ->  // Настройка управления сессиями
                        session.sessionCreationPolicy(  // НЕ создавать HttpSession, НЕ хранить пользователя на сервере, Только JWT
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth  // Начинаем описывать: "Кто куда может ходить"

                        // AUTH
                        .requestMatchers("/auth/**").permitAll() //Разрешаем всем login, register без токена

                        // PRODUCTS
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll() // Просмотр товаров доступен всем (публичный API).
                        .requestMatchers(HttpMethod.POST, "/products/**").permitAll() // Создавать товары может ТОЛЬКО ADMIN.

                        // ORDERS
                        .requestMatchers("/orders/**").permitAll() // Работать с заказами может только залогиненный USER.

                        .requestMatchers("/send").permitAll()

                        // USERS
                        .requestMatchers("/users/**").permitAll()  // Управление пользователями — только ADMIN.

                        .anyRequest().authenticated() // Всё остальное Требует аутентификации
                )

                .authenticationProvider(authenticationProvider()) // Подключаем наш provider: берёт UserDetailsService, сравнивает пароль
                                                                  // Возвращает Authentication
                .addFilterBefore(jwtFilter, // Вставляем JWT фильтр: JW Filter->Security Context->Controllers
                        UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Собираем всю конфигурацию в SecurityFilterChain.
    }

    @Bean // Создаём бин аутентификатора.
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);  // Передаем CustomUserDetailsService. Он будет загружать пользователя из БД.

        provider.setPasswordEncoder(passwordEncoder()); // Указываем: Как сравнивать пароль: raw password -> BCrypt -> compare

        return provider; // Возвращаем provider в Spring контейнер.
    }

    @Bean // Создаём бин кодировщика.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Используем BCrypt: salt-> adaptive hashing->production standard
    }

    @Bean // Получаем готовый AuthenticationManager из Spring.
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager(); // Spring сам собирает: provider, encoder, userDetailsService
    }
}

