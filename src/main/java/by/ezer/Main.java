package by.ezer;

import by.ezer.entity.Order;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.repository.OrderRepository;
import by.ezer.repository.ProductRepository;
import by.ezer.repository.UserRepository;
import by.ezer.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    static void main() {
        UserRepository userRepo = new UserRepository();
        ProductRepository productRepo = new ProductRepository();
        OrderRepository orderRepo = new OrderRepository();

        User us1 = new User("Eduard", 23, "pakachun48@gmail.com");
        Product pr1 = new Product("PC", new BigDecimal("5000.00"), "Cool");
        Product pr2 = new Product("Mouse", new BigDecimal("50.00"), "Small");
        Order order = new Order(LocalDateTime.now(), new BigDecimal("5050.00"));

//        1
        us1.addOrder(order);
        order.setUser(us1);

//        2
        order.addProduct(pr1);
        order.addProduct(pr2);

//        3
        userRepo.save(us1);

        System.out.println("All saved, order id: " + order.getId());
    }
}
