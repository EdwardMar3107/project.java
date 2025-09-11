package by.ezer.repositories.impl;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.User;
import by.ezer.repositories.api.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final Session session;
    public UserRepositoryImpl(Session session) {
        this.session = session;
    }

    @Override
    public List<User> findAll() throws DatabaseException {
        try {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User findById(Long id) throws DatabaseException {
        try {
            return session.find(User.class, id);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void create(User user) throws DatabaseException {
        try {
            session.persist(user);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        try {
            session.merge(user);
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(Long id) throws DatabaseException {
        try {
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
        } catch (HibernateException e) {
            throw new DatabaseException(e);
        }
    }
}
