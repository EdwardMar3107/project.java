package by.ezer.dto;

import java.util.List;

public record OrderCreateDTO(Long userId, List<Long> productIds) {
}
