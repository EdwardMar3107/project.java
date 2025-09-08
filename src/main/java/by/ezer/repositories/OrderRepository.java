package by.ezer.repositories;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.utils.DatabaseConnection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class OrderRepository {

    private final Session session;

    public OrderRepository(Session session) {
        this.session = session;
    }

    public List<Order> findAll() throws DatabaseException {
        try {
            Query<Order> query = session.createQuery("FROM Order", Order.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при получении всех заказов: " + e.getMessage(), e);
        }
    }

    public Order findById(Long id) throws DatabaseException {
        try {
            return session.find(Order.class, id);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при поиске заказа с ID " + id + ": " + e.getMessage(), e);
        }
    }

    public void create(Order order) throws DatabaseException {
        try {
            session.persist(order);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при создании заказа: " + e.getMessage(), e);
        }
    }

    public void update(Order order) throws DatabaseException {
        try {
            session.merge(order);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при обновлении заказа: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) throws DatabaseException {
        try {
            Order order = session.find(Order.class, id);
            if (order != null) {
                session.remove(order);
            }
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при удалении заказа: " + e.getMessage(), e);
        }
    }
}
