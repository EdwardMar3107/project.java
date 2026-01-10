package by.ezer.repositories.impl;

import by.ezer.dto.PagedResult;
import by.ezer.entity.Order;
import by.ezer.exceptions.RepositoryException;
import by.ezer.repositories.api.OrderRepository;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    //Пояснение в UserRepository
    @Override
    public void save(Order order) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Пояснение в UserRepository
    @Override
    public Optional<Order> findById(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            Order order = em.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Есть похожее объяснение в UserRepository
    @Override
    public List<Order> findByUserId(Long userId) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Зачем FETCH JOIN?
    //Без него при обращении к order.getProducts() или order.getUser() вне сессии была бы ошибка LazyInitializationException.
    @Override
    public Optional<Order> findByIdWithDetails(Long id) {
        //Открываем сессию с БД
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            //Создаём запрос к БД с помощью JPQL
            TypedQuery<Order> query = em.createQuery(
                    //"Выбери заказ (назовём его o) из всех заказов"
                    "SELECT o FROM Order o " +
                            //o.products — список продуктов в заказе (lazy-связь),
                            //FETCH заставляет Hibernate загрузить продукты сразу вместе с заказом
                            //LEFT — даже если продуктов нет, заказ всё равно вернётся
                            "LEFT JOIN FETCH o.products " +
                            "LEFT JOIN FETCH o.user " +
                            //Ищем заказ с конкретным id.
                            "WHERE o.id = :id", Order.class);
            //Подставляем реальное значение id в запрос.
            query.setParameter("id", id);
            //Пытаемся получить единственный результат, который оборачиваем в Optional
            try {
                return Optional.of(query.getSingleResult());
            } catch (jakarta.persistence.NoResultException e) {
                //Если - нет, возвращаем пустой Optional без ошибки
                return Optional.empty();
            }
        }
    }

    //Пояснение в UserRepository
    @Override
    public PagedResult<Order> findAllPaged(int page, int size) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            //Запрос на данные страницы
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
            query.setFirstResult(page * size);
            query.setMaxResults(size);
            List<Order> content = query.getResultList();

            //Запрос на общее количество записей
            TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(o) FROM Order o", Long.class);
            Long totalElements = countQuery.getSingleResult();

            return new PagedResult<>(content, page, size, totalElements);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Пояснение в UserRepository
    @Override
    public void update(Order order) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            Order order = em.find(Order.class, id);
            if (order != null) {
                em.remove(order);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
