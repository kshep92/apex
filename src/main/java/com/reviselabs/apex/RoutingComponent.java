package com.reviselabs.apex;

import com.reviselabs.apex.web.RoutingContext;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Created by Kevin on 12/21/2016.
 */
public class RoutingComponent {
    protected Vertx vertx;
    protected Router router;

    public interface RequestHandler {
        void handle(RoutingContext context);
    }

    {
        vertx = Vertx.vertx();
        router = Router.router(vertx);
    }

    protected Router getRouter() {
        return router;
    }

    protected Vertx getVertx() {
        return vertx;
    }

    private Route doRoute(HttpMethod method, String url, RequestHandler handler) {
        return router.route(method, url)
                .handler(context -> handler.handle(createContext(context)));
    }

    protected Route get(String url, RequestHandler handler) {
        return doRoute(HttpMethod.GET, url, handler);
    }

    protected Route post(String url, RequestHandler handler) {
        return doRoute(HttpMethod.POST, url, handler);
    }

    protected Route put(String url, RequestHandler handler) {
        return doRoute(HttpMethod.PUT, url, handler);
    }

    protected Route delete(String url, RequestHandler handler) {
        return doRoute(HttpMethod.DELETE, url, handler);
    }

    private RoutingContext createContext(io.vertx.ext.web.RoutingContext context) {
        return new RoutingContext().withDelegate(context);
    }


}
