package by.ezer.mappers;

import by.ezer.dto.OrderDTO;
import by.ezer.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default", uses = ProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "products", source = "products")
    OrderDTO toDto(Order order);
}