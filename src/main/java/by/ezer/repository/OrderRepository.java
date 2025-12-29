package by.ezer.repository;

import by.ezer.entity.Order;
import by.ezer.exceptions.RepositoryException;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class OrderRepository {

    public void save(Order order) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<Order> findById(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            Order order = em.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public List<Order> findByUserId(Long userId) {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId", Order.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public List<Order> findAll() {
        try(EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

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
