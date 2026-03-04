package by.ezer.mappers;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(Product product);
}
