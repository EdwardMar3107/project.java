package by.ezer.repositories.impl;


import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
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
    public List<Product> findAll() throws  RepositoryException {
        try {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Product findById(Long id) throws  RepositoryException {
        try {
            return session.find(Product.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void create(Product product) throws RepositoryException {
        try {
            session.persist(product);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Product product)  throws RepositoryException {
        try {
            session.merge(product);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        try {
            Product product = session.find(Product.class, id);
            if (product != null) {
                session.remove(product);
            }
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }
}



