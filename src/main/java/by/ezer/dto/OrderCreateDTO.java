package by.ezer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateDTO {

    private Long userId;
    private LocalDate date;
    private String status;
    private List<Long> productIds;

    public OrderCreateDTO(Long userId, LocalDate date, String status, List<Long> productIds) {
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.productIds = productIds != null ? new ArrayList<>(productIds) : new ArrayList<>();
    }
}
