package by.ezer.mappers.api;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import by.ezer.entity.User;

public interface ProductMapper {
    ProductDTO toDto(Product product);
}
