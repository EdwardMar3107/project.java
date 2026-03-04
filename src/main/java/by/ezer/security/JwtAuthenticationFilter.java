package by.ezer.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// Перехватывает каждый запрос, проверяет JWT, логинит пользователя
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Фильтр выполнится 1 раз на запрос.


    private final JwtService jwtService; // // Сервис для: парсинга токена, проверки подписиизвлечения username
    private final CustomUserDetailsService userDetailsService; // Получения пользователя из БД по email

    @Override
    protected void doFilterInternal( // Spring вызывает ЭТОТ метод: На каждый HTTP запрос.
            HttpServletRequest request,  // Входящий HTTP запрос: headers, body, url
            HttpServletResponse response, // HTTP ответ.
            FilterChain filterChain) // Цепочка фильтров.
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization"); // Читаем Authorization header. Достаём:  Authorization: Bearer eyJhbGci...

        if (authHeader == null || // Проверка есть ли токен: Если заголовка нет
                !authHeader.startsWith("Bearer ")) { // Или не начинается с:

            filterChain.doFilter(request, response); // Передаём запрос дальше: Без логина пользователя.
            return; // Останавливаем выполнение фильтра.
        }

        // Обрезаем Bearer: Было: Bearer eyJhbGci... Стало: eyJhbGci...
        String token = authHeader.substring(7);
        String username =
                jwtService.extractUsername(token); // Достаём username из JWT. JWT внутри содержит: subject = email

        if (username != null && // Если username успешно извлечён
                SecurityContextHolder.getContext() // И если пользователь ЕЩЁ НЕ залогинен в этом запросе
                        .getAuthentication() == null) {

            UserDetails userDetails =  // Загружаем пользователя из БД
                    userDetailsService
                            .loadUserByUsername(username);  // По email ищем пользователя в БД.

            if (jwtService.isTokenValid(token, userDetails)) { // Проверяет: подпись, срок действия, соответствие username

                // Создаём Authentication объект
                UsernamePasswordAuthenticationToken authToken = // Создаём объект"Этот пользователь авторизован"
                        new UsernamePasswordAuthenticationToken(
                                userDetails, // Кладём пользователя
                                null, // Пароль НЕ нужен (JWT уже проверен)
                                userDetails.getAuthorities() // Роли пользователя
                        );

                // Сохраняем пользователя в SecurityContext
                SecurityContextHolder.getContext() // Теперь Spring считает: Пользователь ЗАЛОГИНЕН И: @PreAuthorize работает, hasRole работает,
                        .setAuthentication(authToken); // Controller знает user
            }
        }
        // Передаём запрос дальше
        filterChain.doFilter(request, response); // Отдаём запрос: Controller, другим фильтрам
    }
}

//JwtAuthenticationFilter:
//
//Каждый запрос:
//
//1️⃣ Проверяет JWT
//2️⃣ Валидирует токен
//3️⃣ Загружает пользователя
//4️⃣ Логинит в SecurityContext
//5️⃣ Пропускает дальше

