package by.ezer.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
@Slf4j
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    @SneakyThrows
    private static SessionFactory buildSessionFactory() {
        log.info("Initializing SessionFactory");
        return new Configuration().configure().buildSessionFactory();
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            log.info("SessionFactory closed");
        }
    }
}
