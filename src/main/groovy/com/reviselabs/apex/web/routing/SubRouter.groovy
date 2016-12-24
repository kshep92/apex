package com.reviselabs.apex.web.routing

import com.google.inject.Inject
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

abstract class SubRouter implements ApplicationContextContainer, RoutingComponent, ISubRouter {

    @Inject
    private void createRouter(Vertx vertx) {
        assert vertx != null
        router = Router.router(vertx)
    }

    @Override
    def <T> T getInstance(Class<T> clazz) {
        return applicationContext.getInstance(clazz)
    }

    @Override
    ApexRoutingContext createContext(RoutingContext context) {
        return new ApexRoutingContext(applicationContext: applicationContext).withDelegate(context)
    }
}
