package by.ezer.cache.impl;

import by.ezer.cache.Cache;

import java.util.LinkedHashMap;

public class LRUCache implements Cache {
    // LRUCache — реализация кэша по алгоритму LRU (Least Recently Used)
    // LRU = "удаляем то, что использовали давнее всего"

    private final int capacity;
    // Максимальное количество элементов, которое может хранить кэш

    private final LinkedHashMap<Long, Object> cache;
    // Основное хранилище кэша
    // LinkedHashMap сохраняет порядок элементов

    public LRUCache(int capacity) {
        // Конструктор кэша

        this.capacity = capacity;
        // Сохраняем максимальный размер кэша

        this.cache = new LinkedHashMap<>(capacity);
        // Создаём LinkedHashMap с начальной ёмкостью
    }

    @Override
    public Object get(Long key) {
        // Метод получения значения по ключу

        if (containsKey(key)) {
            // Если ключ есть в кэше

            Object value = cache.get(key);
            // Получаем значение по ключу

            cache.put(key, value);
            // Перекладываем элемент в конец LinkedHashMap
            // Таким образом помечаем его как "использованный недавно"

            return value;
            // Возвращаем значение
        }
        return null;
        // Если ключа нет — возвращаем null
    }

    @Override
    public void put(Long key, Object object) {
        // Метод добавления элемента в кэш

        if (cache.size() == capacity) {
            // Если кэш уже заполнен

            Long firstKey = cache.keySet().iterator().next();
            // Берём первый ключ в LinkedHashMap
            // Это самый "старый" элемент (давно не использовался)

            cache.remove(firstKey);
            // Удаляем самый старый элемент
        }

        cache.put(key, object);
        // Добавляем новый элемент в кэш
        // Он автоматически становится "самым свежим"
    }

    @Override
    public void delete(Long key) {
        cache.remove(key);
    }

    @Override
    public boolean containsKey(Long key) {
        // Проверяем, есть ли ключ в кэше

        return cache.containsKey(key);
        // Возвращаем true или false
    }
}
