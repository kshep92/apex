package com.reviselabs.apex

import com.google.inject.Inject
import com.reviselabs.apex.config.ApexConfiguration
import com.reviselabs.apex.di.ApplicationContextContainer
import com.reviselabs.apex.di.DependencyManager
import com.reviselabs.apex.web.routing.RoutingComponent
import com.reviselabs.apex.web.routing.SubRouter
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
//TODO: Configuring of static files
class ApexApplication implements RoutingComponent, ApplicationContextContainer {
    private Logger logger;
    @Inject Vertx vertx
    @Inject HttpServer server;
    private ApexConfiguration configuration;

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
        DependencyManager.initializeWith(configuration).injectMembers(this);
        assert vertx != null
        router = Router.router(vertx)
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

    void start(int port = configuration.serverOptions.port, Handler<AsyncResult> callback = {}) {
        server.requestHandler(router.&accept)
                .listen(port, { result ->
                        if(result.succeeded()) {
                            logger.info("Listening on port ${port}...");
                            callback.handle(result);
                        } else logger.error(result.cause().message);
                    });
    }

    void start(Handler<AsyncResult> callback) {
        start(configuration.serverOptions.port, callback)
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

    ApexApplication mount(String prefix, Class<? extends SubRouter> subRouterClass) {
        SubRouter subRouter = getInstance(subRouterClass)
        subRouter.parent = this.router
        subRouter.configure()
        router.mountSubRouter(prefix, subRouter.router)
        return this;
    }

    private static class BaseConfiguration extends ApexConfiguration {
        @Override
        protected void configure() {

        }
    }

    /*private class Something {
        @Delegate StaticHandler delegate = create();
    }*/

}
