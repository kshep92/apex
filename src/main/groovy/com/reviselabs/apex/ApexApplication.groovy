package com.reviselabs.apex

import com.reviselabs.apex.RoutingComponent.RequestHandler
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CookieHandler

/**
 * Created by Kevin on 12/21/2016.
 */
class ApexApplication {
    @Delegate RoutingComponent delegate;

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
                        println("Listening on port ${port}...");
                        if(callback) callback.handle(result);
                    });
    }

    void stop(Handler<AsyncResult<Object>> callback) {
        vertx.close({result -> callback.handle(result)});
    }

    void stop() {
        print("App is shutting down...");
        stop({
            result -> if(result.succeeded()) {
                println("Bye!")
                vertx.close();
            }
        })
    }
}
