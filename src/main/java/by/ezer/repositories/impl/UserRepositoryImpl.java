package by.ezer.repositories.impl;

import by.ezer.dto.PagedResult;
import by.ezer.entity.User;
import by.ezer.exceptions.RepositoryException;
import by.ezer.repositories.api.UserRepository;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    //Метод, который сохраняет пользователя
    @Override
    public void save(User user) {
        //Соединяемся с БД
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            //Открываем транзакцию
            em.getTransaction().begin();
            //Сохраняем пользователя
            em.persist(user);
            //Коммитим транзакцию
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Поиск пользователя по id
    @Override
    public Optional<User> findById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            //Находим пользователя по id
            User user = em.find(User.class, id);
            //Возвращаем пользователя и оборачиваем если есть, если null - вернется пустой Optional
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public PagedResult<User> findAllPaged(int page, int size) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.name ASC", User.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            List<User> list = query.getResultList();

            TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(u) FROM User u", Long.class);
            Long totalElements = countQuery.getSingleResult();

            return new PagedResult<>(list, page, size, totalElements);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Optional чтобы не было null
    @Override
    public Optional<User> findByName(String name) {
        //Открываем TWR
      try (EntityManager em = HibernateUtil.getEntityManager();) {
          //JPQL запрос: Найди пользователей, у которых поле name равно переданному параметру у объекта
          TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
          //Устанавливаем значение параметра name
          query.setParameter("name", name);
          //Пытаемся получить единственный результат
          try {
              //Если пользователь найдеН, то оборачиваем его в Optional
              return Optional.of(query.getSingleResult());
          } catch (jakarta.persistence.NoResultException e) {
              //Если - нет, то возвращаем пустой Optional без ошибки
              return Optional.empty();
          }
      } catch (Exception e) {
          throw new RepositoryException(e);
      }
    }

    //Пояснение в методу findByName
    @Override
    public Optional<User> findByEmail(String email) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            try {
                return Optional.of(query.getSingleResult());
            } catch (jakarta.persistence.NoResultException e) {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RepositoryException("Error finding user by email: " + email, e);
        }
    }

    @Override
    public void update(User user) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            //Обновляем пользователя
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
