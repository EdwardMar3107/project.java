package by.ezer.repositories.impl;

import by.ezer.dto.PagedResult;
import by.ezer.entity.Order;
import by.ezer.repositories.api.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    //Spring сам инжектит текущий EntityManager
    @PersistenceContext
    private EntityManager em;

    //Пояснение в UserRepository
    @Override
    public void save(Order order) {
        em.persist(order);
    }

    //Пояснение в UserRepository
    @Override
    public Optional<Order> findById(Long id) {
        Order order = em.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    //Зачем FETCH JOIN?
    //Без него при обращении к order.getProducts() или order.getUser() вне сессии была бы ошибка LazyInitializationException.
    @Override
    public Optional<Order> findByIdWithDetails(Long id) {
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
        } catch (NoResultException e) {
            //Если - нет, возвращаем пустой Optional без ошибки
            return Optional.empty();
        }
    }


    //Пояснение в UserRepository
    @Override
    public PagedResult<Order> findAllPaged(int page, int size) {
            //Запрос на данные страницы
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Order> content = query.getResultList();

            //Запрос на общее количество записей
        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(o) FROM Order o", Long.class);
        Long totalElements = countQuery.getSingleResult();

        return new PagedResult<>(content, page, size, totalElements);
    }

    //Пояснение в UserRepository
    @Override
    public void update(Order order) {
        em.merge(order);
    }

    @Override
    public void deleteById(Long id) {
            Order order = em.find(Order.class, id);
            if (order != null) {
                em.remove(order);
            }
    }
}
