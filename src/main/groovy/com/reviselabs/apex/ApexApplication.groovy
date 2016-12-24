package com.reviselabs.apex
import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.reviselabs.apex.config.ApexConfiguration
import com.reviselabs.apex.web.RouteGroup
import com.reviselabs.apex.web.RoutingComponent
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ApexApplication implements RoutingComponent {
    private Logger logger;
    @Inject HttpServer server;
    private ApexConfiguration configuration;
    private Injector injector;

    {
        logger = LoggerFactory.getLogger(getClass())
    }

    ApexApplication() {
        configuration = new BaseConfiguration()
        init()
    }

    ApexApplication(ApexConfiguration configuration) {
        this.configuration = configuration
        init()
    }

    // Set up dependency injection
    private void init() {
        injector = Guice.createInjector(configuration);
        injector.injectMembers(this)
    }

    @Override
    com.reviselabs.apex.web.RoutingContext createContext(RoutingContext context) {
        return new com.reviselabs.apex.web.RoutingContext(applicationContext: this).withDelegate(context)
    }

    ApexApplication addHandler(Handler<RoutingContext> handler, HttpMethod... methods) {
        if(methods) methods.each { router.route().method(it).handler(handler) }
        else router.route().handler(handler);
        return this;
    }

    ApexApplication parseCookies() {
        addHandler(CookieHandler.create());
        return this;
    }

    ApexApplication parseRequestBody() {
        addHandler(BodyHandler.create(), HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);
        return this;
    }

    def <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    void start(int port = configuration.serverConfig().port, Handler<AsyncResult> callback = {}) {
        server.requestHandler(router.&accept)
                .listen(port, { result ->
                        if(result.succeeded()) {
                            logger.info("Listening on port ${port}...");
                            callback.handle(result);
                        } else logger.error(result.cause().message);
                    });
    }

    void start(Handler<AsyncResult> callback) {
        start(configuration.serverConfig().port, callback)
    }

    void stop(Handler<AsyncResult<Object>> callback = {}) {
        logger.info("App is shutting down...");
        vertx.close({ result ->
            if(result.succeeded()) {
                logger.info("Bye!")
                callback.handle(result)
            } else logger.error(result.cause().message)
        });
    }

    ApexApplication mount(String prefix, Class<? extends RouteGroup> subRouteClass) {
        def group = injector.getInstance(subRouteClass)
        group.applicationContext = this
        router.mountSubRouter(prefix, group.router)
        return this
    }

    private static class BaseConfiguration extends ApexConfiguration {}

    /*private class Something {
        @Delegate StaticHandler delegate = create();
    }*/

}
