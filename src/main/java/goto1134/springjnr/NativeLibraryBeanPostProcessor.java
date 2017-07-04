package goto1134.springjnr;

import jnr.ffi.LibraryLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads native libraries and injects them to the  fields
 */
public class NativeLibraryBeanPostProcessor
        implements BeanPostProcessor {
    private Map<Class<?>, Object> loadedLibrariesMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        // TODO: 04.07.2017 Find out if concurrent access is available

        for (Field field : bean.getClass()
                               .getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectNativeLibrary.class)) {
                Class<?> type = field.getType();
                if (!type.isInterface()) {
                    throw new IllegalArgumentException(
                            "Class of type " + type.getSimpleName() +
                                    " must be an interface to be loaded as native library");
                }
                if (!type.isAnnotationPresent(NativeLibrary.class)) {
                    throw new IllegalArgumentException("Interface " + type.getSimpleName() +
                                                               " must annotated with @NativeLibrary annotation to be " +
                                                               "loaded  as native library");
                }
                Object library = loadedLibrariesMap.computeIfAbsent(type, (c) -> {
                    NativeLibrary annotation = c.getAnnotation(NativeLibrary.class);
                    return LibraryLoader.create(c)
                                        .load(annotation.libraryName());
                });
                field.setAccessible(true);
                try {
                    field.set(bean, library);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Native library field must not be static");
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
