package com.reviselabs.apex.web
import com.reviselabs.apex.web.RequestHandler
import com.reviselabs.apex.web.RoutingContext
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

class RoutingComponent {
    protected Vertx vertx
    protected Router router

    {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
    }

    public Router getRouter() {
        return router;
    }

    public Vertx getVertx() {
        return vertx;
    }

    private Route doRoute(HttpMethod method, String url, RequestHandler handler) {
        return router.route(method, url)
                .handler({context -> handler.handle(createContext(context))});
    }

    public Route get(String url, RequestHandler handler) {
        return doRoute(HttpMethod.GET, url, handler);
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

    private static RoutingContext createContext(io.vertx.ext.web.RoutingContext context) {
        return new RoutingContext().withDelegate(context);
    }
}
