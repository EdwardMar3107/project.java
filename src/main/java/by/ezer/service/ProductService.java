package by.ezer.service;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.ProductMapper;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
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
        if (productCreateDTO == null) {
            log.error("productCreateDTO is null");
            throw new RepositoryException("ProductCreateDTO cannot be null");
        }
        if (productCreateDTO.getName() == null || productCreateDTO.getPrice() == null) {
            log.error("productCreateDTO.getName() or productCreateDTO.getPrice() is null");
            throw new RepositoryException("Name and price are required");
        }

        Product product = ProductMapper.INSTANCE.toEntity(productCreateDTO);

        productRepository.create(product);
        log.info("Product created successfully");
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public ProductDTO getProductById(Long id) throws RepositoryException {
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
        if (productDTO == null || productDTO.getId() == null) {
            log.error("productDTO is null");
            throw new RepositoryException("ProductDTO or ID cannot be null");
        }
        Product existingProduct = productRepository.findById(productDTO.getId());
        if (existingProduct == null) {
            handleNotFound(productDTO.getId());
        }

        ProductMapper.INSTANCE.updateProductFromDTO(productDTO, existingProduct);
        productRepository.update(existingProduct);
        log.info("Product updated successfully");
    }

    public void deleteProduct(Long id) throws RepositoryException {
        productRepository.delete(id);
        log.info("Product deleted successfully");
    }

    private void handleNotFound(Long id) throws RepositoryException {
        String errorMessage = "Product with id " + id + " not found";
        log.error(errorMessage);
        throw new RepositoryException(errorMessage);
    }
}
