package by.ezer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
    private LocalDate createdAt;

    public ProductDTO(Long id, String name, BigDecimal price, Boolean isAvailable, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
    }
}
