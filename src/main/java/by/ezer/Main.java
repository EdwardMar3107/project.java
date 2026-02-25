package by.ezer;

import by.ezer.config.AppConfig;
import by.ezer.service.OrderService;
import by.ezer.service.ProductService;
import by.ezer.service.UserService;
import by.ezer.test.EmailTestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main {
    static void main(String[] args) {

        // Запускаем Spring Boot приложение
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        // Получаем наш тестовый сервис из контекста Spring
        EmailTestService emailTestService = context.getBean(EmailTestService.class);

        // Отправляем тестовое письмо сразу после запуска
        emailTestService.sendTestEmail();
    }
}