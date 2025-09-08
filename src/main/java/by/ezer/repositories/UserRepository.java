package by.ezer.repositories;



import by.ezer.exceptions.DatabaseException;
import by.ezer.models.User;
import by.ezer.utils.DatabaseConnection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private final Session session;
    public UserRepository(Session session) {
        this.session = session;
    }

    public List<User> findAll() throws DatabaseException {
        try {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при получении всех пользователей: " + e.getMessage(), e);
        }
    }

    public User findById(Long id) throws DatabaseException {
        try {
            return session.find(User.class, id);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при поиске пользователя с ID " + id + ": " + e.getMessage(), e);
        }
    }

    public void create(User user) throws DatabaseException {
        try {
            session.persist(user);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при создании пользователя: " + e.getMessage(), e);
        }
    }

    public void update(User user) throws DatabaseException {
        try {
            session.merge(user);
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при обновлении пользователя: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) throws DatabaseException {
        try {
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
        } catch (HibernateException e) {
            throw new DatabaseException("Ошибка при удалении пользователя: " + e.getMessage(), e);
        }
    }
}
