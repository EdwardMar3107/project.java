package by.ezer;

import by.ezer.exceptions.DatabaseException;
import by.ezer.models.Order;
import by.ezer.models.Product;
import by.ezer.models.User;
import by.ezer.repositories.impl.OrderRepositoryImpl;
import by.ezer.repositories.impl.ProductRepositoryImpl;
import by.ezer.repositories.impl.UserRepositoryImpl;
import by.ezer.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, DatabaseException {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            UserRepositoryImpl userRepository = new UserRepositoryImpl(session);
            ProductRepositoryImpl productRepository = new ProductRepositoryImpl(session);
            OrderRepositoryImpl orderRepository = new OrderRepositoryImpl(session);

            try {
                User user = new User("Иван", "Иванов", "ivanov", "password123", LocalDate.of(1990, 5, 15));
                userRepository.create(user);
                System.out.println("Создан пользователь: " + user.getName() + " " + user.getSurname());

                Product product1 = new Product("Ноутбук", new BigDecimal("999.99"), true, LocalDate.now());
                Product product2 = new Product("Мышь", new BigDecimal("29.99"), true, LocalDate.now());
                Product product3 = new Product("Клавиатура", new BigDecimal("49.99"), false, LocalDate.now());

                productRepository.create(product1);
                productRepository.create(product2);
                productRepository.create(product3);
                System.out.println("Созданы продукты: " + product1.getName() + ", " + product2.getName() + ", " + product3.getName());

                Order order = new Order(user, LocalDate.now(), "новый", (java.util.Set<Product>) Arrays.asList(product1, product2));
                orderRepository.create(order);
                System.out.println("Создан заказ #" + order.getId() + " для пользователя " + user.getName());

                System.out.println("Все пользователи:");
                userRepository.findAll().forEach(u ->
                        System.out.println(u.getId() + ": " + u.getName() + " " + u.getSurname())
                );

                System.out.println("Все продукты:");
                productRepository.findAll().forEach(p ->
                        System.out.println(p.getId() + ": " + p.getName() + " - " + p.getPrice() + " (доступен: " + p.getIsAvailable() + ")")
                );

                System.out.println("Все заказы:");
                orderRepository.findAll().forEach(o ->
                        System.out.println(o.getId() + ": пользователь " + o.getUser().getName() + ", статус: " + o.getStatus())
                );

                User foundUser = userRepository.findById(user.getId());
                System.out.println("Найден пользователь по ID: " + foundUser.getName());

                product1.setPrice(new BigDecimal("1099.99"));
                productRepository.update(product1);
                System.out.println("Обновлена цена продукта: " + product1.getName() + " - " + product1.getPrice());

                productRepository.delete(product3.getId());
                System.out.println("Удален продукт: " + product3.getName());

                Product deletedProduct = productRepository.findById(product3.getId());
                System.out.println("Попытка найти удаленный продукт: " + (deletedProduct == null ? "не найден" : "найден"));

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) transaction.rollback();
                System.err.println("Ошибка: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }

    }
}
