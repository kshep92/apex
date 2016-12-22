package com.reviselabs.apex.web

import io.vertx.core.Handler
import io.vertx.ext.web.Route
import io.vertx.ext.web.impl.BlockingHandlerDecorator
import io.vertx.ext.web.impl.RouteImpl

class ApexRouteImpl implements Route, ApexRoute {

    @Delegate RouteImpl delegate

    public synchronized Route handler(Handler<io.vertx.ext.web.RoutingContext> contextHandler) {
        if (this.contextHandler != null) {
            log.warn("Setting handler for a route more than once!");
        }
        this.contextHandler = contextHandler;
        checkAdd();
        return this;
    }

    public synchronized Route handler(Handler<RoutingContext> handler) {
        this.contextHandler = handler;
        return this;
    }

    public Route blockingHandler(Handler<io.vertx.ext.web.RoutingContext> contextHandler) {
        return blockingHandler(contextHandler, true);
    }

    public synchronized Route blockingHandler(Handler<io.vertx.ext.web.RoutingContext> contextHandler, boolean ordered) {
        return handler(new BlockingHandlerDecorator(contextHandler, ordered));
    }

    public synchronized Route failureHandler(Handler<io.vertx.ext.web.RoutingContext> exceptionHandler) {
        if (this.failureHandler != null) {
            log.warn("Setting failureHandler for a route more than once!");
        }
        this.failureHandler = exceptionHandler;
        checkAdd();
        return this;
    }
}
