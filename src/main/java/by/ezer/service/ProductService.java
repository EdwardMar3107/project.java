package by.ezer.service;

import by.ezer.dto.PagedResult;
import by.ezer.dto.ProductDTO;
import by.ezer.entity.Product;
import by.ezer.mappers.ProductMapper;
import by.ezer.repositories.api.ProductRepository;
import by.ezer.repositories.impl.ProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO createProduct(String productName, BigDecimal price, String description) {

        Product product = new Product(productName, price, description);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDTO getProductById(Long id) {

        Optional<Product> productOpt = productRepository.findById(id);

        Product product = productOpt.orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toDto(product);
    }

    public PagedResult<ProductDTO> getAllPaged(int page, int size) {

        PagedResult<Product> result = productRepository.findAllPaged(page, size);

        List<ProductDTO> dtos = result.getContent().stream()
                .map(productMapper::toDto)
                .toList();

        return new PagedResult<>(dtos, result.getPage(), result.getSize(), result.getTotalElements());
    }

    public List<ProductDTO> findProductsByName(String namePart) {
        //Вызываем репозиторий
        List<Product> products = productRepository.findByNameContaining(namePart);

        //Преобразуем каждый Product в ProductDTO с помощью маппера
        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public void updateProduct(Long productId, ProductDTO dto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPrice(dto.price());
        product.setDescription(dto.description());

        productRepository.update(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
