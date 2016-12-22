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
}
