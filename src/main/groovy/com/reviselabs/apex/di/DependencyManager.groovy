package com.reviselabs.apex.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.reviselabs.apex.config.ApexConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependencyManager {
    private static Injector injector;
    private static Logger logger = LoggerFactory.getLogger(DependencyManager.class);

    public static Injector initializeWith(ApexConfiguration configuration) {
        if(injector == null) {
            logger.debug("Initializing injector");
            injector = Guice.createInjector(configuration);
        }
        return getInjector();
    }

    public static Injector getInjector() {
        if(injector == null) {
            logger.error("Application hasn't been configured yet.");
            return null;
        }
        return injector;
    }

    private DependencyManager() {}
}
