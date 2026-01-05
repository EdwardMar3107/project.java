package by.ezer.repositories.api;

import by.ezer.entity.User;
import by.ezer.exceptions.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user) throws RepositoryException;
    Optional<User> findById(Long id) throws RepositoryException;
    List<User> findAll() throws RepositoryException;
    Optional<User> findByName(String name) throws RepositoryException;
    Optional<User> findByEmail(String email) throws RepositoryException;
    void update(User user) throws RepositoryException;
    void deleteById(Long id) throws RepositoryException;
}
