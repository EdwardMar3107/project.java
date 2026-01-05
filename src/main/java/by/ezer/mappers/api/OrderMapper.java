package by.ezer.mappers.api;

import by.ezer.dto.OrderDTO;
import by.ezer.entity.Order;

public interface OrderMapper {
    OrderDTO toDto(Order order);
}
