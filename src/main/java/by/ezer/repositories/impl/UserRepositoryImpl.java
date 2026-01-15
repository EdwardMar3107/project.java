package by.ezer.repositories.impl;

import by.ezer.dto.PagedResult;
import by.ezer.entity.User;
import by.ezer.repositories.api.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    //Метод, который сохраняет пользователя
    @Override
    public void save(User user) {
        //Сохраняем пользователя
        em.persist(user);
    }

    //Поиск пользователя по id
    @Override
    public Optional<User> findById(Long id) {
        //Находим пользователя по id
        User user = em.find(User.class, id);
        //Возвращаем пользователя и оборачиваем если есть, если null - вернется пустой Optional
        return Optional.ofNullable(user);
    }

    @Override
    public PagedResult<User> findAllPaged(int page, int size) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.userName ASC", User.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<User> list = query.getResultList();

        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(u) FROM User u", Long.class);
        Long totalElements = countQuery.getSingleResult();

        return new PagedResult<>(list, page, size, totalElements);
    }

    @Override
    public void update(User user) {
        //Обновляем пользователя
        em.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }
}
