package by.ezer;

import by.ezer.config.SpringConfig;
import by.ezer.dto.OrderDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.dto.UserDTO;
import by.ezer.service.OrderService;
import by.ezer.service.ProductService;
import by.ezer.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

            UserService userService = context.getBean(UserService.class);
            ProductService productService = context.getBean(ProductService.class);
            OrderService orderService = context.getBean(OrderService.class);

            userService.deleteUser(1L);
            productService.deleteProduct(1L);
            orderService.deleteOrder(1L);

            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}