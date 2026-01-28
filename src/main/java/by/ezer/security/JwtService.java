package by.ezer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service // Spring создаёт singleton сервис.
public class JwtService {

    private final String SECRET_KEY =
            "VERY_SECRET_KEY_123456789"; // Секретный ключ для подписи JWT. В реальном проекте — в env variable.

    private final long EXPIRATION =
            1000 * 60 * 60 * 24; // Время жизни токена: 24 часа

    // Генерация токена. Метод создаёт JWT.
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder() // Builder JWT.
                .setSubject(userDetails.getUsername()) // Subject = email пользователя.
                .setIssuedAt(new Date())  // Дата создания.
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION) // Когда токен истечёт.
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Подписываем токен: HMAC + SHA256.
                .compact(); // Генерируем строку JWT.
    }

    // Извлечение email из токена. Получаем email из токена.
    public String extractUsername(String token) {

        return getClaims(token) // Subject = email.
                .getSubject();
    }

    // Проверка токена
    public boolean isTokenValid(String token,
                                UserDetails userDetails) {

        String username = extractUsername(token); // Достаём email.

        return username.equals(userDetails.getUsername()) // Проверяем: email совпадает, срок не истёк
                && !isTokenExpired(token);
    }

    // ======================
    // Приватный метод — используется ТОЛЬКО внутри JwtService.Проверить: истёк ли срок действия JWT.
    private boolean isTokenExpired(String token) {

        return getClaims(token) // Парсим JWT. Проверяем подпись. Достаём payload (claims)
                .getExpiration() // Из payload берём поле: exp (expiration)
                .before(new Date());
    }

    // Это критический метод безопасности.  Claims — это JWT payload:subject, expiration, issuedAt, roles, custom fields
    private Claims getClaims(String token) {

        return Jwts.parser() // Создаём JWT parser. Parser умеет: проверять подпись, декодировать Base64, валидировать формат
                .setSigningKey(getSignKey()) // Указываем: КАКИМ ключом проверять подпись.
                .build() // Собираем parser instance.
                .parseClaimsJws(token) // Здесь происходит: JWT делится на 3 части, Проверяется подпись,
                .getBody(); // Получаем payload (Claims).
    }

    // Это криптографическая часть.
    private Key getSignKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(SECRET_KEY); // JWT библиотека требует: бинарный ключ (byte[]), не строку
        // Это нужно потому что: HMAC работает ТОЛЬКО с байтами.
        // HMAC (Hash-based Message Authentication Code) - Способ подписывать данные секретным ключом, чтобы можно было проверить:
        // что данные НЕ изменились, что их подписал тот, кто знает секрет
        return Keys.hmacShaKeyFor(keyBytes); // Создаём HMAC-SHA ключ. JWT потом использует этот Key для: подписи токена, проверки подлинности
    }
}

