package by.ezer.service;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(Session session) {
        this.productRepository = new  by.ezer.repositories.impl.ProductRepositoryImpl(session);
    }

    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) throws DatabaseException {
        if (productCreateDTO == null) {
            throw new DatabaseException("ProductCreateDTO cannot be null");
        }
        Product product = new Product(
                productCreateDTO.getName(),
                productCreateDTO.getPrice(),
                productCreateDTO.getIsAvailable(),
                productCreateDTO.getCreatedAt()
        );
        productRepository.create(product);
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getIsAvailable(), product.getCreatedAt());
    }

    public ProductDTO getProductById(Long id) throws DatabaseException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new DatabaseException("Product with id " + id + " not found");
        }
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getIsAvailable(), product.getCreatedAt());
    }

    public List<ProductDTO> getAllProducts() throws DatabaseException {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getIsAvailable(), product.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public void updateProduct(ProductDTO productDTO) throws DatabaseException {
        if (productDTO == null || productDTO.getId() == null) {
            throw new DatabaseException("ProductDTO or ID cannot be null");
        }
        Product existingProduct = productRepository.findById(productDTO.getId());
        if (existingProduct == null) {
            throw new DatabaseException("Product with ID " + productDTO.getId() + " not found");
        }
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setIsAvailable(productDTO.getIsAvailable());
        existingProduct.setCreatedAt(productDTO.getCreatedAt());
        productRepository.update(existingProduct);
    }

    public void deleteProduct(Long id) throws DatabaseException {
        productRepository.delete(id);
    }
}
