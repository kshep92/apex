package com.reviselabs.apex.web.routing

import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.ext.web.RoutingContext

class ApexRoutingContext implements ApplicationContextContainer {
    @Delegate RoutingContext context;

    ApexRoutingContext(RoutingContext context) {
        this.context = context;
    }

    ApexRoutingContext ok() {
        response().setStatusCode(200);
        return this;
    }

    ApexRoutingContext badRequest() {
        response().setStatusCode(400);
        return this;
    }

    ApexRoutingContext forbidden() {
        response().setStatusCode(401);
        return this;
    }

    void close(String message = '') {
        response().end(message);
    }
}
