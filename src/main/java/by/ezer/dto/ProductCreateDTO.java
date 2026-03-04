package by.ezer.dto;

import java.math.BigDecimal;

public record ProductCreateDTO(String productName, BigDecimal price, String description) {
}
