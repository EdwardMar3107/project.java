package by.ezer.dto.orderDTO;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDTO {
    private Long id;
    private Long userId;
    private LocalDate date;
    private String status;
    private List<Long> productIds;
}
