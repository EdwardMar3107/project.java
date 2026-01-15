package by.ezer.repositories.api;

import by.ezer.dto.PagedResult;
import by.ezer.entity.Product;
import by.ezer.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product) throws RepositoryException;
    Optional<Product> findById(Long id) throws RepositoryException;
    PagedResult<Product> findAllPaged(int page, int size) throws RepositoryException;
    void update(Product product) throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
}
