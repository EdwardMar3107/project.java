package by.ezer.aspect;

import by.ezer.aspect.annotation.Loggable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingAspect {

    @Pointcut("hasLoggableOnClass() && @annotation(by.ezer.aspect.annotation.Loggable)")
    public void hasLoggableOnMethod() {
    }

    @Pointcut("@within(by.ezer.aspect.annotation.Loggable)")
    public void hasLoggableOnClass() {
    }

    @Around("hasLoggableOnMethod() || hasLoggableOnClass()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // Запоминаем время начала выполнения метода
        long start = System.currentTimeMillis();

        // Получаем имя класса (OrderService, ProductService и т.д.)
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // Получаем имя метода (createOrder, findById и т.д.)
        String methodName = joinPoint.getSignature().getName();

        // Аргументы, с которыми вызвали метод
        Object[] args = joinPoint.getArgs();

        // Лог ДО вызова метода. BEFORE
        log.info(
                "[LOG] --> {}.{}({})",
                className,
                methodName,
                argsToString(args)
        );

        try {
            // ВАЖНО!!!
            // Здесь реально вызывается метод сервиса
            Object result = joinPoint.proceed();

            // Считаем сколько миллисекунд выполнялся метод
            long duration = System.currentTimeMillis() - start;

            // Лог ПОСЛЕ успешного выполнения. AfterReturning
            log.info(
                    "[LOG] <-- {}.{}() returned {} | {}ms",
                    className,
                    methodName,
                    result,
                    duration
            );

            // Обязательно возвращаем результат метода
            return result;

        } catch (Exception ex) {

            // Если метод выбросил ошибку
            long duration = System.currentTimeMillis() - start;

            // Лог ошибки. AfterThrowing
            log.error(
                    "[LOG] <x> {}.{}() threw {}: \"{}\" | {}ms",
                    className,
                    methodName,
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    duration
            );

            // ОБЯЗАТЕЛЬНО пробрасываем исключение дальше
            throw ex;
        }
    }

    // Вспомогательный метод:
    // превращает аргументы метода в строку
    private String argsToString(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append(", ");
        }

        // Убираем последнюю запятую и пробел
        return sb.substring(0, sb.length() - 2);
    }
}
