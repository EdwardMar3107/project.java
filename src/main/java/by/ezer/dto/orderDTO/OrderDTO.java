package by.ezer.dto.orderDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private LocalDate date;
    private String status;
    private List<Long> productIds;

    public OrderDTO(Long id, Long userId, LocalDate date, String status, List<Long> productIds) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.productIds = productIds != null ? new ArrayList<>(productIds) : new ArrayList<>();;
    }
}
