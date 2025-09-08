package by.ezer.repositories;


import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Product;
import by.ezer.utils.DatabaseConnection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductRepository {

    private Session session;

    public ProductRepository(Session session) {
        this.session = session;
    }

    public List<Product> findAll() throws  DatabaseException {
        try {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при получении всех продуктов: " + e.getMessage(), e);
        }
    }

    public Product findById(Long id) throws  DatabaseException {
        try {
            return session.find(Product.class, id);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при поиске продукта с ID " + id + ": " + e.getMessage(), e);
        }
    }

    public void create(Product product) throws DatabaseException {
        try {
            session.persist(product);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при создании продукта: " + e.getMessage(), e);
        }
    }

    public void update(Product product)  throws DatabaseException {
        try {
            session.merge(product);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при обновлении продукта: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) throws  DatabaseException {
        try {
            Product product = session.find(Product.class, id);
            if (product != null) {
                session.remove(product);
            }
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при удалении продукта: " + e.getMessage(), e);
        }
    }
}



