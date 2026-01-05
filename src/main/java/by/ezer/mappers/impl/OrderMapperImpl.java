package by.ezer.mappers.impl;

import by.ezer.dto.OrderDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.entity.Order;
import by.ezer.mappers.api.OrderMapper;
import by.ezer.mappers.api.ProductMapper;

import java.util.List;

public class OrderMapperImpl implements OrderMapper {

    private final ProductMapper productMapper;

    public OrderMapperImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

        List<ProductDTO> productDTOs = order.getProducts().stream()
                .map(productMapper::toDto)
                .toList();

        return OrderDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .userName(order.getUser().getUsername())
                .userEmail(order.getUser().getEmail())
                .products(productDTOs)
                .build();
    }
}
