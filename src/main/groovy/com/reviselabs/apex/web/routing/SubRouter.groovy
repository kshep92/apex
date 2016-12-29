package com.reviselabs.apex.web.routing

import com.google.inject.Inject
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

abstract class SubRouter implements ApplicationContextContainer, RoutingComponent, ISubRouter {
    Router parent

    @Inject
    private void _setRouter(Vertx vertx) {
        assert vertx != null
        router = Router.router(vertx)
    }
}
