package by.ezer.repository;

import by.ezer.entity.Order;
import by.ezer.exceptions.RepositoryException;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class OrderRepository {

    //Пояснение в UserRepository
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
    public Optional<Order> findById(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            Order order = em.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Есть похожее объяснение в UserRepository
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
    public List<Order> findAll() {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    //Пояснение в UserRepository
    public void update(Order order) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

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
