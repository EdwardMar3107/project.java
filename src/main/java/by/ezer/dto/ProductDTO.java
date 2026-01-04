package by.ezer.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Builder
@Accessors(chain = true)
public record ProductDTO(Long id, String name, BigDecimal price, String description) {
    //DTO - класс, который нужен для того, чтобы показать содержимое БД - без вмешательства в БД(картинку)
    // ProductDTO — Data Transfer Object
    // Используется для передачи данных о продукте наружу (в Main, API, фронтенд)
    // Содержит только нужные поля из сущности Product
    // Не имеет lazy-связей → безопасно использовать вне сессии Hibernate (нет LazyInitializationException)
    // Не позволяет случайно изменить данные в БД
}
