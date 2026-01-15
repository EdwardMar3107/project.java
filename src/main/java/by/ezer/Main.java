package by.ezer;

import by.ezer.config.AppConfig;
import by.ezer.service.OrderService;
import by.ezer.service.ProductService;
import by.ezer.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}