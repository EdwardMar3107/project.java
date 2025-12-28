package by.ezer;

import by.ezer.entity.User;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;

public class Main {
    static void main() {
        EntityManager em = HibernateUtil.getEntityManager();
        em.getTransaction().begin();

        User user = new User("Anton", 30, "antoshkakartoshka@gmail.com");
        em.persist(user);

        em.getTransaction().commit();
        em.close();

        System.out.println("User has been created" + user);
    }
}
