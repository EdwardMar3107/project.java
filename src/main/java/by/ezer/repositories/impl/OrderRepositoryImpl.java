package by.ezer.repositories.impl;

import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
import by.ezer.models.Order;
import by.ezer.repositories.api.OrderRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class OrderRepositoryImpl implements OrderRepository {

    private final Session session;

    public OrderRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<Order> findAll() throws RepositoryException {
        try {
            Query<Order> query = session.createQuery("FROM Order", Order.class);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Order findById(Long id) throws RepositoryException {
        try {
            return session.find(Order.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void create(Order order) throws RepositoryException {
        try {
            session.persist(order);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Order order) throws RepositoryException {
        try {
            session.merge(order);
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        try {
            Order order = session.find(Order.class, id);
            if (order != null) {
                session.remove(order);
            }
        } catch (HibernateException e) {
            throw new RepositoryException(e);
        }
    }
}
