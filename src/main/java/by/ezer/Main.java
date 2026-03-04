package by.ezer;

import by.ezer.config.AppConfig;
import by.ezer.service.OrderService;
import by.ezer.service.ProductService;
import by.ezer.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main {
    static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
