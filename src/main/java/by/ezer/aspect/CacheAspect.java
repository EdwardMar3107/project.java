package by.ezer.aspect;

import by.ezer.aspect.annotation.Cacheable;
import by.ezer.cache.Cache;
import by.ezer.cache.impl.LRUCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CacheAspect {

    private final Cache cache = new LRUCache(20);
    // Создаём кэш LRU на 20 элементов
    // Кэш живёт внутри аспекта

    @Pointcut("execution(public !void by.ezer.service.*.findById(..))")
    public void findByIdMethod() {
    }
    // Pointcut:
    // Перехватывает все public методы findById(..)
    // в пакетe by.ezer.service.*
    // !void — метод должен что-то возвращать

    @Pointcut("execution(public !void by.ezer.service.*.update(..))")
    public void updateMethod() {
    }
    // Pointcut для всех методов update(..)

    @Pointcut("execution(public !void by.ezer.service.*.save(..))")
    public void saveMethod() {
    }
    // Pointcut для всех методов save(..)
    // (в этом аспекте не используется, но объявлен)

    @Pointcut("execution(public !void by.ezer.service.*.delete(..))")
    public void deleteMethod() {
    }
    // Pointcut для всех методов delete(..)

    @Around("findByIdMethod()")
    // Around-advice:
    // код выполнится ДО и ПОСЛЕ findById(...)
    public Object cachingFindByIdResult(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean hasAnnotation =
                joinPoint.getTarget()
                        .getClass()
                        .isAnnotationPresent(Cacheable.class);
        // Проверяем:
        // помечен ли класс сервиса аннотацией @Cacheable

        if (hasAnnotation) {
            // Если кэширование включено для этого сервиса

            Long id = (Long) joinPoint.getArgs()[0];
            // Берём первый аргумент метода (предполагаем, что это id)

            if (cache.containsKey(id)) {
                // Если объект уже есть в кэше

                return cache.get(id);
                // Возвращаем значение из кэша
                // Сам метод findById НЕ вызывается
            } else {
                // Если в кэше нет

                Object retVal = joinPoint.proceed();
                // Вызываем реальный метод findById(...)

                cache.put(id, retVal);
                // Сохраняем результат в кэш

                return retVal;
                // Возвращаем результат
            }
        }

        return joinPoint.proceed();
        // Если аннотации нет — просто вызываем метод без кэширования
    }

    @Around("updateMethod()")
    // Around-advice для методов update(...)
    public Object cachingUpdateResult(ProceedingJoinPoint joinPoint) throws Throwable {

        boolean hasAnnotation =
                joinPoint.getTarget()
                        .getClass()
                        .isAnnotationPresent(Cacheable.class);
        // Проверяем наличие @Cacheable

        if (hasAnnotation) {
            // Если кэширование включено

            Long id = (Long) joinPoint.getArgs()[0];
            // Получаем id объекта

            Object object = joinPoint.proceed();
            // Выполняем реальный метод update(...)

            if (cache.containsKey(id)) {
                // Если объект есть в кэше

                cache.put(id, object);
                // Обновляем значение в кэше

                return object;
                // Возвращаем обновлённый объект
            }
        }

        return joinPoint.proceed();
        // Если аннотации нет — просто выполняем метод
    }

    @Around("deleteMethod()")
    // Around-advice для методов delete(...)
    public void cachingDeleteMethod(ProceedingJoinPoint joinPoint) {

        boolean hasAnnotation =
                joinPoint.getTarget()
                        .getClass()
                        .isAnnotationPresent(Cacheable.class);
        // Проверяем наличие @Cacheable

        if (hasAnnotation) {
            // Если кэширование включено

            Long id = (Long) joinPoint.getArgs()[0];
            // Получаем id объекта

            if (cache.containsKey(id)) {
                // Если объект есть в кэше

                cache.delete(id);
                // Удаляем объект из кэша
            }
        }
        // Реальный delete(...) метод НЕ вызывается здесь
        // (что является логической ошибкой — ниже поясню)
    }
}
