package by.ezer;

import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    static void main() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();
        }
    }
}
