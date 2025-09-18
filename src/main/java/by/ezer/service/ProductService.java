package by.ezer.service;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.ProductMapper;
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

    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) throws RepositoryException {
        if (productCreateDTO == null) {
            throw new RepositoryException("ProductCreateDTO cannot be null");
        }
        if (productCreateDTO.getName() == null || productCreateDTO.getPrice() == null) {
            throw new RepositoryException("Name and price are required");
        }

        Product product = ProductMapper.INSTANCE.toEntity(productCreateDTO);

        productRepository.create(product);
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public ProductDTO getProductById(Long id) throws RepositoryException {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new RepositoryException("Product with id " + id + " not found");
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
            throw new RepositoryException("ProductDTO or ID cannot be null");
        }
        Product existingProduct = productRepository.findById(productDTO.getId());
        if (existingProduct == null) {
            throw new RepositoryException("Product with ID " + productDTO.getId() + " not found");
        }

        ProductMapper.INSTANCE.updateProductFromDTO(productDTO, existingProduct);

        productRepository.update(existingProduct);
    }

    public void deleteProduct(Long id) throws RepositoryException {
        productRepository.delete(id);
    }
}
