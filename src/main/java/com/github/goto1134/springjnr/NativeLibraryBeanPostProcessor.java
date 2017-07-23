package com.github.goto1134.springjnr;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Loads native libraries and injects them to the  fields
 */
public class NativeLibraryBeanPostProcessor
        implements BeanPostProcessor {
    private final SpringJNRLibraryLoader mSpringJNRLibraryLoader = new SpringJNRLibraryLoader();
    private Map<Class<?>, Object> loadedLibrariesMap = new ConcurrentHashMap<>();
    private Map<Class<? extends Annotation>, LibraryInfo> configurations = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        for (Field field : bean.getClass()
                               .getDeclaredFields()) {
            if (field.isAnnotationPresent(NativeLibrary.class)) {
                Class<?> libraryType = field.getType();
                if (!libraryType.isInterface()) {
                    throw new IllegalArgumentException(
                            "Class of type " + libraryType.getSimpleName() +
                                    " must be an interface to be loaded as native library");
                }

                Class<? extends Annotation> qualifier = getQualifier(libraryType)
                        .orElseThrow(() -> new IllegalArgumentException("Library interface " + libraryType
                                .getName() + " must have a qualifier"));
                LibraryInfo libraryInfo = configurations.get(qualifier);
                Object library = loadedLibrariesMap.computeIfAbsent(libraryType,
                                                                    c -> mSpringJNRLibraryLoader.load(c, libraryInfo));
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

    private Optional<Class<? extends Annotation>> getQualifier(Class<?> aClass) {
        for (Annotation annotation : aClass.getAnnotations()) {
            Class<? extends Annotation> qualifierCandidate = annotation.annotationType();
            if (qualifierCandidate.isAnnotationPresent(Qualifier.class)) {
                return Optional.of(qualifierCandidate);
            }
        }
        return Optional.empty();
    }

    @Autowired
    void setNativeLibraryConfiguration(List<NativeLibraryConfiguration> aConfigurations) {
        for (NativeLibraryConfiguration configuration : aConfigurations) {
            Class<? extends Annotation> qualifier = getQualifier(configuration.getClass()).orElseThrow(
                    () -> new IllegalArgumentException("NativeLibraryConfiguration should have a qualifier"));
            configurations.put(qualifier, configuration.getLibraryInfo());
        }
    }
}
