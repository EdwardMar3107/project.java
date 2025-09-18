package by.ezer.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ValidationUtils {

    public static void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkId(Long id, String entityType) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(entityType + " ID must be positive");
        }
    }

    public static void checkCollectionNotEmpty(List<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
