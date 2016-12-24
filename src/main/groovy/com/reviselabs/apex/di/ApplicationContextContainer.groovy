package com.reviselabs.apex.di

import com.reviselabs.apex.ApexApplication
import org.slf4j.Logger

// Using a trait instead of an interface since interface fields are public static final (Java rule, not Groovy)
trait ApplicationContextContainer {
    ApexApplication applicationContext;
    Logger logger

    abstract <T> T getInstance(Class<T> clazz);
}