package by.ezer.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Builder
@Accessors(chain = true)
public record ProductDTO(Long id, String name, BigDecimal price, String description) {
}
