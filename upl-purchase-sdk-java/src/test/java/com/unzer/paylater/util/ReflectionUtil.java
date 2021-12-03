package com.unzer.paylater.util;

import java.lang.reflect.Field;

import org.junit.Assert;

public final class ReflectionUtil {

    private ReflectionUtil() {}

    public static <T> T getField(Object object, String fieldName, Class<T> fieldType) {
        // Retrieve the field from the given class or its parent.
        Class<?> clazz = object.getClass();
        while (clazz != Object.class) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                Assert.assertTrue(fieldType.isInstance(fieldValue));
                return fieldType.cast(fieldValue);
            } catch (@SuppressWarnings("unused") NoSuchFieldException e) {
                // try next class
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            clazz = clazz.getSuperclass();
        }

        return null;
    }
}
