package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.exceptions.RepositoryException;
import by.ezer.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll() throws RepositoryException;
    User findById(Long id) throws RepositoryException;
    void create(User user) throws RepositoryException;
    void update(User user) throws RepositoryException;
    void delete(Long id) throws RepositoryException;
}
