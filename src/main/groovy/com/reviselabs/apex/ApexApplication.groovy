package com.reviselabs.apex

import com.reviselabs.apex.web.RoutingComponent
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Kevin on 12/21/2016.
 */
class ApexApplication {
    @Delegate RoutingComponent delegate;
    Logger logger;

    ApexApplication() {
        delegate = new RoutingComponent();
        logger = LoggerFactory.getLogger(getClass())
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

    void listen(int port, Handler<AsyncResult> callback) {
        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(port?: 3000,
                    { result ->
                        logger.info("Listening on port ${port}...");
                        if(callback) callback.handle(result);
                    });
    }

    void listen(int port) {
        listen(port, null);
    }

    void stop(Handler<AsyncResult<Object>> callback = {}) {
        logger.info("App is shutting down...");
        vertx.close({ result ->
            if(result.succeeded()) {
                logger.info("Bye!")
                callback.handle(result)
            } else logger.error("Server shutdown error!")
        });
    }
}
