package by.ezer.dto.productDTO;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductCreateDTO {
    private String name;
    private BigDecimal price;
    private Boolean isAvailable;
    private LocalDate createdAt;
}
