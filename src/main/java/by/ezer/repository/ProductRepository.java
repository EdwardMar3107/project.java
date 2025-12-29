package by.ezer.repository;

import by.ezer.entity.Product;
import by.ezer.exceptions.RepositoryException;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductRepository {

    public void save(Product product) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<Product> findById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            Product product = em.find(Product.class, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public List<Product> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public void update(Product product) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public void deleteById(Long id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }
}
