package by.ezer.dto;

import lombok.Builder;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Accessors(chain = true)
public record OrderDTO(Long id, LocalDateTime orderDate, BigDecimal totalAmount, String userName, String userEmail,
                       List<ProductDTO> products) {
    //DTO - класс, который нужен для того, чтобы показать содержимое БД - без вмешательства в БД(картинку)
    // OrderDTO — Data Transfer Object
    // Используется для передачи данных о продукте наружу (в Main, API, фронтенд)
    // Содержит только нужные поля из сущности Product
    // Не имеет lazy-связей → безопасно использовать вне сессии Hibernate (нет LazyInitializationException)
    // Не позволяет случайно изменить данные в БД
}
