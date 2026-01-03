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
}
