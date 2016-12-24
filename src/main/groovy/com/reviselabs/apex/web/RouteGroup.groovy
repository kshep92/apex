package com.reviselabs.apex.web
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.core.logging.Logger

class RouteGroup implements ApplicationContextContainer, RoutingComponent {

    protected Logger logger

    @Override
    def <T> T getInstance(Class<T> clazz) {
        return applicationContext.getInstance(clazz)
    }

    @Override
    RoutingContext createContext(io.vertx.ext.web.RoutingContext context) {
        return new RoutingContext(applicationContext: applicationContext).withDelegate(context)
    }
}
