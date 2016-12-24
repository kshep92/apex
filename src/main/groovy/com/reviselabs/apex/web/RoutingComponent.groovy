package com.reviselabs.apex.web

import com.google.inject.Inject
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

import java.util.logging.Handler

trait RoutingComponent {
    @Inject Vertx vertx
    Router router = Router.router(vertx)

    private Route doRoute(HttpMethod method, String url, RequestHandler handler) {
        return router.route(method, url)
                .handler({context -> handler.handle(createContext(context))});
    }

    public Route get(String url, RequestHandler handler) {
        return doRoute(HttpMethod.GET, url, handler);
    }

    // TODO: Get this to work
    public void get(String url, Handler... handlers) {
        handlers.each { handler -> doRoute(HttpMethod.GET, url, handler as RequestHandler) }
    }

    public Route post(String url, RequestHandler handler) {
        return doRoute(HttpMethod.POST, url, handler);
    }

    public Route put(String url, RequestHandler handler) {
        return doRoute(HttpMethod.PUT, url, handler);
    }

    public Route delete(String url, RequestHandler handler) {
        return doRoute(HttpMethod.DELETE, url, handler);
    }

    public Route before(String url, RequestHandler handler) {
        return router.route(url).handler({context -> handler.handle(createContext(context))})
    }

    public Route before(RequestHandler handler) {
        return router.route().handler({context -> handler.handle(createContext(context))})
    }

    abstract RoutingContext createContext(io.vertx.ext.web.RoutingContext context)

}