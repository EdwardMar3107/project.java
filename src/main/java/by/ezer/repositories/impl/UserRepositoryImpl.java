package by.ezer.repositories.impl;

import by.ezer.exceptions.RepositoryException;
import by.ezer.models.User;
import by.ezer.repositories.api.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final Session session;
    private Transaction transaction;
    private static final String FROM_USER = "FROM User";
    public UserRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(FROM_USER, User.class);
            List<User> users = query.list();
            transaction.commit();
            return users;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public User findById(Long id) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            transaction.commit();
            return user;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void create(User user) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(User user) throws RepositoryException {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.merge(user);
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
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
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