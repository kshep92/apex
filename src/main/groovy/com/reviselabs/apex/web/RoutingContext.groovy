package com.reviselabs.apex.web

/**
 * Created by Kevin on 12/21/2016.
 */
class RoutingContext {
    @Delegate io.vertx.ext.web.RoutingContext context;

    RoutingContext withDelegate(io.vertx.ext.web.RoutingContext context) {
        this.context = context;
        return this;
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
