package by.ezer.mappers.impl;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import by.ezer.mappers.api.ProductMapper;

public class ProductMapperImpl  implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
