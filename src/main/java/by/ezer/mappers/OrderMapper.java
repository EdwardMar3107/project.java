package by.ezer.mappers;

import by.ezer.dto.OrderDTO;
import by.ezer.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "userEmail", source = "user.email")
    OrderDTO toDto(Order order);
}