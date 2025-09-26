package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll() throws RepositoryException;
    Order findById(Long id) throws RepositoryException;
    void create(Order order) throws RepositoryException;
    void update(Order order) throws RepositoryException ;
    void delete(Long id) throws RepositoryException;
}
