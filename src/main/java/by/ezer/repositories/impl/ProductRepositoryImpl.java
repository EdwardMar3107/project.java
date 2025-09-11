package by.ezer.repositories.impl;


import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class ProductRepositoryImpl implements ProductRepository {

    private final Session session;

    public ProductRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Product> findAll() throws  DatabaseException {
        try {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Product findById(Long id) throws  DatabaseException {
        try {
            return session.find(Product.class, id);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void create(Product product) throws DatabaseException {
        try {
            session.persist(product);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(Product product)  throws DatabaseException {
        try {
            session.merge(product);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(Long id) throws  DatabaseException {
        try {
            Product product = session.find(Product.class, id);
            if (product != null) {
                session.remove(product);
            }
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }
}



