package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findAll() throws DatabaseException;
    Order findById(Long id) throws DatabaseException;
    void create(Order order) throws DatabaseException;
    void update(Order order) throws DatabaseException ;
    void delete(Long id) throws DatabaseException;
}
