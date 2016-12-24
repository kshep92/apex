package com.reviselabs.apex.web

import com.reviselabs.apex.di.ApplicationContextContainer

class RoutingContext implements ApplicationContextContainer {
    @Delegate io.vertx.ext.web.RoutingContext context;

    RoutingContext withDelegate(io.vertx.ext.web.RoutingContext context) {
        this.context = context;
        return this;
    }

    @Override
    def <T> T getInstance(Class<T> clazz) {
        return applicationContext.getInstance(clazz)
    }

    RoutingContext ok() {
        response().setStatusCode(200);
        return this;
    }

    RoutingContext badRequest() {
        response().setStatusCode(400);
        return this;
    }

    RoutingContext forbidden() {
        response().setStatusCode(401);
        return this;
    }

    void close(String message = '') {
        response().end(message);
    }
}
