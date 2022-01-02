package net.crossager.common.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MethodUtils {
    public static Method getMethod(Class<?> clazz, String name, boolean forceAccess, Class<?>... params) throws IllegalArgumentException, NoSuchMethodException {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(name);
        Method method = clazz.getDeclaredMethod(name, params);
        if(forceAccess)
            method.setAccessible(true);
        return method;
    }
    public static Method getMethod(Object o, String name, boolean forceAccess, Class<?>... params) throws IllegalArgumentException, NoSuchMethodException {
        return getMethod(o.getClass(), name, forceAccess, params);
    }
    public static Method getMethod(Object o, String name, boolean forceAccess, Object... params) throws NoSuchMethodException {
        Class<?>[] args = {};
        for(int i = 0; i < params.length; i++){
            args[i] = params[i].getClass();
        }
        return getMethod(o, name, forceAccess, args);
    }
    public static Method getMethod(Object o, String name) throws NoSuchMethodException {
        return getMethod(o, name, false);
    }
    public static Object invokeMethod(Object o, String name, Object... args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getMethod(o, name, true, args).invoke(o, args);
    }
}
