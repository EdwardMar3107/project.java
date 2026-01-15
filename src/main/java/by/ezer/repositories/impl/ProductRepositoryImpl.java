package by.ezer.repositories.impl;

import by.ezer.dto.PagedResult;
import by.ezer.entity.Product;
import by.ezer.repositories.api.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager em;

    //Пояснение в UserRepository
    @Override
    public void save(Product product) {
        em.persist(product);
    }

    //Пояснение в UserRepository
    @Override
    public Optional<Product> findById(Long id) {
        Product product = em.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    //Пояснение в UserRepository
    @Override
    public PagedResult<Product> findAllPaged(int page, int size) {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p ORDER BY p.productName ASC", Product.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Product> products = query.getResultList();

        TypedQuery<Long> countQuery = em.createQuery("SELECT COUNT(p) FROM Product p", Long.class);
        Long totalElements = countQuery.getSingleResult();

        return new PagedResult<>(products, page, size, totalElements);
    }

    //Пояснение в UserRepository
    @Override
    public void update(Product product) {
        em.merge(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
        }
    }
}
