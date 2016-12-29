package com.reviselabs.apex.di;

public interface ApplicationContextContainer {
    default <T> T getInstance(Class<T> clazz) {
        return DependencyManager.getInjector().getInstance(clazz);
    }
}
