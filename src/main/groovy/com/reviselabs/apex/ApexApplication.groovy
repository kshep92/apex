package com.reviselabs.apex

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.reviselabs.apex.config.ApexConfiguration
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

class ApexApplication {
    @Delegate private RoutingComponent delegate;
    protected static Logger logger;
    @Inject HttpServer server;
    private ApexConfiguration configuration;
    private Injector injector;

    {
        logger = LoggerFactory.getLogger(getClass())
        configuration = new BaseConfiguration()
    }

    ApexApplication() {
        hydrate()
    }

    ApexApplication(ApexConfiguration configuration) {
        this.configuration = configuration
        hydrate()
    }

    RoutingComponent getDelegate() {
        return delegate
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

    private void hydrate() {
        injector = Guice.createInjector(configuration);
        delegate = injector.getInstance(RoutingComponent)
        injector.injectMembers(this)
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

    private static class BaseConfiguration extends ApexConfiguration {
        {
            logger.debug("Initializing")
        }
    }

    /*private class Something {
        @Delegate StaticHandler delegate = create();
    }*/

}
