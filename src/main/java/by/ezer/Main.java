package by.ezer;

import by.ezer.config.AppConfig;
import by.ezer.service.OrderService;
import by.ezer.service.ProductService;
import by.ezer.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("Server is open: http://localhost:8080/test/hello");
        context.registerShutdownHook();
    }
}