package com.reviselabs.apex.web.routing

import com.google.inject.Inject
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.templ.TemplateEngine

class ApexRoutingContext implements ApplicationContextContainer {
    @Delegate RoutingContext context;
    @Inject TemplateEngine templateEngine;

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

    void end(String message = '') {
        response().end(message);
    }

    //TODO: Fix error where it says Connection Closed
    void render(String template, Map data = [:]) {
        data.forEach({String k, Object v -> this.put(k, v) })
        templateEngine.render(this, template, { result ->
            response().putHeader("content-type", "text/html;charset=UTF-8").end(result.result());
        })
    }
}
