package by.ezer.service;

import by.ezer.dto.productDTO.ProductCreateDTO;
import by.ezer.dto.productDTO.ProductDTO;
import by.ezer.exceptions.RepositoryException;
import by.ezer.mappers.ProductMapper;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO save(ProductCreateDTO productCreateDTO) throws RepositoryException {
        
        Product product = ProductMapper.INSTANCE.toEntity(productCreateDTO);

        productRepository.create(product);
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public ProductDTO findById(Long id) throws RepositoryException {

        Product product = productRepository.findById(id);
        if (product == null) {
            throw new RepositoryException("Product not found");
        }
        return ProductMapper.INSTANCE.toDTO(product);
    }

    public List<ProductDTO> findAll() throws RepositoryException {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public void update(ProductDTO productDTO) throws RepositoryException {
        Product existingProduct = productRepository.findById(productDTO.getId());
        if (existingProduct == null) {
            throw new RepositoryException("Product not found");
        }

        ProductMapper.INSTANCE.updateProductFromDTO(productDTO, existingProduct);
        productRepository.update(existingProduct);
    }

    public void delete(Long id) throws RepositoryException {
        productRepository.delete(id);
    }
}
