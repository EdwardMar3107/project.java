package by.ezer.mappers;

import by.ezer.dto.orderDTO.OrderCreateDTO;
import by.ezer.dto.orderDTO.OrderDTO;
import by.ezer.models.Order;
import by.ezer.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user_id", target = "userId")
    @Mapping(source = "products", target = "productIds", qualifiedByName = "mapProductsToIds")
    OrderDTO toDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "products", ignore = true)
    Order toEntity(OrderCreateDTO dto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateOrderFromDTO(OrderDTO dto, @MappingTarget Order order);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(Set<Product> products) {
        return products != null ? products.stream().map(Product::getId).collect(Collectors.toList()) : new ArrayList<>();
    }
}


