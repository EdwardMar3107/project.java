package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll() throws RepositoryException;
    Product findById(Long id) throws RepositoryException;
    void create(Product product) throws RepositoryException;
    void update(Product product) throws RepositoryException ;
    void delete(Long id) throws RepositoryException;
}
