package com.reviselabs.apex.web
import com.google.inject.Inject
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router

class RoutingComponent implements ApplicationContextContainer {
    private Vertx vertx
    private Router router
    private Logger logger;

    {
        logger = LoggerFactory.getLogger(getClass());
    }

    RoutingComponent() {
        vertx = Vertx.vertx(); // Will be appropriately configured when mounted to an ApexApplication
        init()
    }

    @Inject
    RoutingComponent(Vertx vertx) {
        this.vertx = vertx;
        init()
    }

    private void init() {
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

    private RoutingContext createContext(io.vertx.ext.web.RoutingContext context) {
        return new RoutingContext(applicationContext: applicationContext).withDelegate(context);
    }

    @Override
    def <T> T getInstance(Class<T> clazz) {
        return applicationContext.getInstance(clazz)
    }
}
