package com.reviselabs.apex.di;

public interface ApplicationContextContainer {
    default <T> T getInstance(Class<T> clazz) {
        //noinspection ConstantConditions
        return DependencyManager.getInjector().getInstance(clazz);
    }
}
