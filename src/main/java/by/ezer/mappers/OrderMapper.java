package by.ezer.mappers;

import by.ezer.dto.orderDTO.OrderCreateDTO;
import by.ezer.dto.orderDTO.OrderDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import by.ezer.repositories.api.UserRepository;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "default")
public interface OrderMapper {

    @Mapping(source = "userId", target = "user", expression = "java(userRepository.findById(orderCreateDTO.getUserId()))")
    @Mapping(target = "products", source = "productIds", qualifiedByName = "mapProductIdsToProducts")
    Order toEntity(OrderCreateDTO orderCreateDTO, @Context ProductRepository productRepository, @Context UserRepository userRepository);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "products", target = "productIds", qualifiedByName = "mapProductsToIds")
    OrderDTO toDTO(Order order);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "products", source = "productIds", qualifiedByName = "mapProductIdsToProducts")
    void updateOrderFromDTO(OrderDTO dto, @MappingTarget Order order, @Context ProductRepository productRepository);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(Set<Product> products) {
        return products != null ? products.stream().map(Product::getId).collect(Collectors.toList()) : new ArrayList<>();
    }

    @Named("mapProductIdsToProducts")
    default Set<Product> mapProductIdsToProducts(List<Long> productIds, @Context ProductRepository productRepository) {
        if (productIds == null) return null;
        return productIds.stream()
                .map(productId -> {
                    try {
                        return productRepository.findById(productId);
                    } catch (RepositoryException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}

