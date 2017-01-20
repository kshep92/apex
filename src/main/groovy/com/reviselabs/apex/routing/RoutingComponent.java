package com.reviselabs.apex.routing;

import com.reviselabs.apex.di.DependencyManager;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;

public abstract class RoutingComponent {

    private Router router;

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    private RoutingComponent doRoute(HttpMethod method, String url, RequestHandler handler) {
        router.route(method, url)
                .handler(context -> handler.handle(createContext(context)));
        return this;
    }

    public ApexRoutingContext createContext(RoutingContext context) {
        @SuppressWarnings("ConstantConditions")
        ApexRoutingContext apexRoutingContext = DependencyManager.getInjector().getInstance(ApexRoutingContext.class);
        apexRoutingContext.setContext(context);
        return apexRoutingContext;
    }

    //TODO: Resolve why these routing methods don't work in Java classes
    public RoutingComponent get(String url, RequestHandler handler) {
        return doRoute(HttpMethod.GET, url, handler);
    }

    public RoutingComponent get(String url, RequestHandler... handlers) {
        Arrays.asList(handlers).forEach(handler -> get(url, handler));
        return this;
    }

    public RoutingComponent post(String url, RequestHandler handler) {
        doRoute(HttpMethod.POST, url, handler);
        return this;
    }

    public RoutingComponent post(String url, RequestHandler... handlers) {
        Arrays.asList(handlers).forEach(handler -> post(url, handler));
        return this;
    }

    public RoutingComponent put(String url, RequestHandler handler) {
        return doRoute(HttpMethod.PUT, url, handler);
    }

    public RoutingComponent put(String url, RequestHandler... handlers) {
        Arrays.asList(handlers).forEach(handler -> put(url, handler));
        return this;
    }

    public RoutingComponent delete(String url, RequestHandler handler) {
        return doRoute(HttpMethod.DELETE, url, handler);
    }

    public RoutingComponent delete(String url, RequestHandler... handlers) {
        Arrays.asList(handlers).forEach(handler -> delete(url, handler));
        return this;
    }

    public RoutingComponent before(String url, RequestHandler handler) {
        router.route(url).handler(context -> handler.handle(createContext(context)));
        return this;
    }

    public RoutingComponent before(RequestHandler handler) {
        router.route().handler(context -> handler.handle(createContext(context)));
        return this;
    }

    public ApexRoutingContext wrap(RoutingContext context) {
        return createContext(context);
    }
}
