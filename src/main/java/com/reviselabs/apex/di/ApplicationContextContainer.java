package com.reviselabs.apex.di;

// Implemented in Java to get code completion for Groovy classes.
public interface ApplicationContextContainer {
    default <T> T getInstance(Class<T> clazz) {
        return DependencyManager.getInjector().getInstance(clazz);
    }
}
