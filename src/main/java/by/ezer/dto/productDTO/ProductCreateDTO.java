package by.ezer.dto.productDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProductCreateDTO {

    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
    private LocalDate createdAt;

    public ProductCreateDTO(String name, BigDecimal price, Boolean isAvailable, LocalDate createdAt) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
    }
}
