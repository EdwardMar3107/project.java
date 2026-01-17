package by.ezer;

import by.ezer.config.AppConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Launcher {
    public static void main(String[] args) throws Exception {
        System.out.println("Шаг 1: Создаём Tomcat");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8001);  // порт
        System.out.println("Tomcat создан, порт: " + tomcat.getConnector().getPort());

        System.out.println("Шаг 2: Добавляем контекст");
        Context ctx = tomcat.addContext("", null);

        System.out.println("Шаг 3: Создаём Spring-контекст");
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(AppConfig.class);

        System.out.println("Шаг 4: Устанавливаем ServletContext");
        springContext.setServletContext(ctx.getServletContext());

        System.out.println("Шаг 5: Refresh Spring-контекст");
        springContext.refresh();

        System.out.println("Шаг 6: Создаём DispatcherServlet");
        DispatcherServlet dispatcher = new DispatcherServlet(springContext);
        Tomcat.addServlet(ctx, "dispatcher", dispatcher);
        ctx.addServletMappingDecoded("/*", "dispatcher");

        System.out.println("Шаг 7: Запускаем Tomcat");
        tomcat.start();
        System.out.println("Tomcat стартовал! Проверь netstat.");

        // Проверяем, слушает ли порт
        tomcat.getServer().await();
    }
}
