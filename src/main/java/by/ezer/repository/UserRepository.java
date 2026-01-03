package by.ezer.repository;

import by.ezer.entity.User;
import by.ezer.exceptions.RepositoryException;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepository {

    public void save(User user) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<User> findById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public List<User> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

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

    public void update(User user) {
        try (EntityManager em = HibernateUtil.getEntityManager();) {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

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
