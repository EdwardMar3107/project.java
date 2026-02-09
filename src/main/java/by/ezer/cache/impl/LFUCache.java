package by.ezer.cache.impl;

import by.ezer.cache.Cache;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LFUCache implements Cache {

    private static final class Node {
        // Внутренний класс Node
        // Хранит данные одного элемента кэша

        Object value;
        // Само значение, которое мы кладём в кэш

        int useCount;
        // Сколько раз этот элемент был использован (get)

        long lastGetTime;
        // Время последнего обращения к элементу (для разрешения равенств)

        public Node(Object value, int useCount) {
            this.value = value;
            this.useCount = useCount;
        }
    }

    private final int capacity;
    // Максимальное количество элементов в кэше

    private final Map<Long, Node> cache;
    // Основное хранилище кэша
    // key = Long
    // value = Node (значение + счётчики)

    public LFUCache(@NotNull int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        // Создаём пустой HashMap для хранения данных
    }

    @Override
    public Object get(Long key) {             // Метод получения значения по ключу
        if (!containsKey(key)) {              // Если ключа нет в кэше
            return null;                      // Возвращаем null
        }

        cache.get(key).useCount++;            // Увеличиваем счётчик использования элемента

        cache.get(key).lastGetTime = System.nanoTime();  // Запоминаем время последнего обращения

        return cache.get(key).value;
        // Возвращаем само значение
    }

    @Override
    public void put(Long key, Object object) {       // Метод добавления элемента в кэш
        if (containsKey(key)) {                      // Если такой ключ уже есть в кэше
            cache.get(key).value = object;           // Просто обновляем значение
        }

        if (cache.size() == capacity) {
            // Если кэш уже заполнен

            removeMinUsedNode();
            // Удаляем элемент, который использовался реже всего
        }

        Node node = new Node(object, 0);
        // Создаём новый Node
        // useCount = 0, потому что элемент ещё не запрашивали

        node.lastGetTime = System.nanoTime();
        // Фиксируем время добавления элемента

        cache.put(key, node);
        // Кладём элемент в кэш
    }

    @Override
    public void delete(Long key) {
        cache.remove(key);
    }

    @Override
    public boolean containsKey(Long key) {
        return cache.containsKey(key);
    }

    private void removeMinUsedNode() {
        // Метод удаления "наименее используемого" элемента (LFU)

        int minCount = Integer.MAX_VALUE;
        // Минимальное количество использований
        // Сначала ставим максимально возможное значение

        long currTime = System.nanoTime();
        // Текущее время — нужно для сравнения lastGetTime

        Long minKey = 0L;
        // Ключ элемента, который будем удалять

        for (Long key : cache.keySet()) {
            // Проходимся по всем ключам в кэше

            Node node = cache.get(key);
            // Получаем Node по ключу

            if (
                    node.useCount < minCount
                            || (node.useCount == minCount && node.lastGetTime < currTime)
            ) {
                // Если:
                // 1) useCount меньше текущего минимума
                // ИЛИ
                // 2) useCount равен, но элемент использовался раньше

                minKey = key;
                // Запоминаем ключ кандидата на удаление

                minCount = node.useCount;
                // Обновляем минимальный useCount

                currTime = node.lastGetTime;
                // Обновляем минимальное время
            }
        }
        cache.remove(minKey);
        // Удаляем элемент, который использовался реже всего
    }
}
