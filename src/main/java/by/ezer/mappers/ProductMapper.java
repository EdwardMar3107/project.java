package by.ezer.mappers;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductCreateDTO dto);

    void updateProductFromDTO(ProductDTO dto, @MappingTarget Product product);
}
