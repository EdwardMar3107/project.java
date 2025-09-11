package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll() throws DatabaseException;
    Product findById(Long id) throws DatabaseException;
    void create(Product product) throws DatabaseException;
    void update(Product product) throws DatabaseException ;
    void delete(Long id) throws DatabaseException;
}
