package by.ezer.repositories.api;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll() throws DatabaseException;
    User findById(Long id) throws DatabaseException;
    void create(User user) throws DatabaseException;
    void update(User user) throws DatabaseException;
    void delete(Long id) throws DatabaseException;
}
