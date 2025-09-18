package by.ezer.service;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.ProductMapper;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import by.ezer.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) throws RepositoryException {
        ValidationUtils.checkNotNull(productCreateDTO, "productCreateDTO cannot be null");
        ValidationUtils.checkNotNull(productCreateDTO.getName(), "Product name cannot be null");
        ValidationUtils.checkNotNull(productCreateDTO.getPrice(), "Product price cannot be null");

        Product product = ProductMapper.INSTANCE.toEntity(productCreateDTO);

        productRepository.create(product);
        log.info("Product created successfully");
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public ProductDTO getProductById(Long id) throws RepositoryException {
        ValidationUtils.checkId(id, "Product");

        Product product = productRepository.findById(id);
        if (product == null) {
            handleNotFound(id);
        }
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public List<ProductDTO> getAllProducts() throws RepositoryException {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public void updateProduct(ProductDTO productDTO) throws RepositoryException {
        ValidationUtils.checkNotNull(productDTO, "productDTO cannot be null");
        ValidationUtils.checkId(productDTO.getId(), "Product");

        Product existingProduct = productRepository.findById(productDTO.getId());
        if (existingProduct == null) {
            handleNotFound(productDTO.getId());
        }

        ProductMapper.INSTANCE.updateProductFromDTO(productDTO, existingProduct);
        productRepository.update(existingProduct);
        log.info("Product updated successfully");
    }

    public void deleteProduct(Long id) throws RepositoryException {
        ValidationUtils.checkId(id, "Product");

        productRepository.delete(id);
        log.info("Product deleted successfully");
    }

    private void handleNotFound(Long id) throws RepositoryException {
        String errorMessage = "Product with id " + id + " not found";
        log.error(errorMessage);
        throw new RepositoryException(errorMessage);
    }
}
