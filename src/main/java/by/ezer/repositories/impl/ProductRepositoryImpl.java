package by.ezer.repositories.impl;

import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Product;
import by.ezer.repositories.api.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final Session session;
    private Transaction transaction;
    private static final String FROM_PRODUCT = "FROM Product";

    @Override
    public List<Product> findAll() throws  RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Product> query = session.createQuery(FROM_PRODUCT, Product.class);
            List<Product> products = query.list();
            transaction.commit();
            return products;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public Product findById(Long id) throws  RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.find(Product.class, id);
            transaction.commit();
            return product;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void create(Product product) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Product product)  throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.find(Product.class, id);
            if (product != null) {
                session.remove(product);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }
}



