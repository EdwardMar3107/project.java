package by.ezer.mappers;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "default")
public interface ProductMapper {

    @Mapping(target = "name", source = "productName")
    ProductDTO toDto(Product product);
}
