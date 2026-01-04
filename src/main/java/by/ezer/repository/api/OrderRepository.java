package by.ezer.repository.api;

import by.ezer.entity.Order;
import by.ezer.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order) throws RepositoryException;
    Optional<Order> findById(Long id) throws RepositoryException;
    List<Order> findByUserId(Long userId) throws RepositoryException;
    Optional<Order> findByIdWithDetails(Long id) throws RepositoryException;
    List<Order> findAll() throws RepositoryException;
    void update(Order order)  throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
}
