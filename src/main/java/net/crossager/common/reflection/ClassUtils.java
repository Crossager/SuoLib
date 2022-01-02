package net.crossager.common.reflection;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ClassUtils {
    public static Class<?> getSubClass(Class<?> clazz, String name){
        List<Class<?>> innerClasses = Arrays.asList(clazz.getDeclaredClasses());
        for (Class<?> cl : innerClasses) {
            if (cl.getSimpleName().equals(name)) return cl;
        }
        return null;
    }
    public static <P, T> Function<P, T> getConstructorFunction(Class<P> a, Class<T> b, boolean force) throws NoSuchMethodException {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        Constructor<T> constructor = b.getDeclaredConstructor(a);
        if(constructor == null) throw new NoSuchMethodException("Failed to get function constructor from " + b + ", no such constructor.");
        if(force){
            constructor.setAccessible(true);
        }
        FunctionWithExceptions<P, T, ReflectiveOperationException> funCons = constructor::newInstance;
        return new Function<P, T>() {
            @Override
            public T apply(P p) {
                try {
                    return funCons.apply(p);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
    public static <P, T> Function<P, T> getConstructorFunction(Class<P> a, Class<T> b) throws NoSuchMethodException {
        return ClassUtils.getConstructorFunction(a, b, true);
    }
    @FunctionalInterface
    private interface FunctionWithExceptions<T, R, E extends Exception>{
        R apply(T t) throws E;
    }
}
