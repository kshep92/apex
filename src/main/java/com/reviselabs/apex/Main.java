package com.reviselabs.apex;

import io.netty.handler.codec.http.HttpResponse;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Created by ksheppard on 21/12/2016.
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route().handler(context -> {
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "text/plain");
            response.end("Hello from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(3000);

    }
}
