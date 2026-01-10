package by.ezer.mappers;

import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-10T22:17:37+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.1 (Oracle Corporation)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.name( product.getProductName() );
        productDTO.id( product.getId() );
        productDTO.price( product.getPrice() );
        productDTO.description( product.getDescription() );

        return productDTO.build();
    }
}
