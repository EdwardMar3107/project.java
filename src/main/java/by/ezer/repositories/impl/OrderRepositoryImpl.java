package by.ezer.repositories.impl;

import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Order;
import by.ezer.repositories.api.OrderRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class OrderRepositoryImpl implements OrderRepository {

    private final Session session;
    private Transaction transaction;
    private static final String FROM_ORDER = "FROM Order";
    public OrderRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Order> findAll() throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(FROM_ORDER, Order.class);
            List<Order> orders = query.list();
            transaction.commit();
            return orders;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public Order findById(Long id) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Order order = session.find(Order.class, id);
            transaction.commit();
            return order;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void create(Order order) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Order order) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(order);
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
            Order order = session.find(Order.class, id);
            if (order != null) {
                session.remove(order);
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
