package com.reviselabs.apex.di

import com.reviselabs.apex.ApexApplication

// Using a trait instead of an interface since interface fields are public static final (Java rule, not Groovy)
trait ApplicationContextContainer {
    ApexApplication applicationContext;

    abstract <T> T getInstance(Class<T> clazz);
}