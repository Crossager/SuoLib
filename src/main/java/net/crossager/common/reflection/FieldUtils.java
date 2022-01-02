package net.crossager.common.reflection;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldUtils {
    public static Object readField(Object o, String name, boolean forceAccess) throws IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
        Objects.requireNonNull(o, "Instance or name cannot be null.");
        Objects.requireNonNull(name, "Instance or name cannot be null.");
        Class<?> clazz = o.getClass();
        Field field = clazz.getDeclaredField(name);
        if(forceAccess)
            field.setAccessible(true);
        return field.get(o);
    }
    public static Object readField(Object o, String name) throws NoSuchFieldException, IllegalAccessException {
        return readField(o, name, false);
    }
}
