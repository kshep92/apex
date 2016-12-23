package com.reviselabs.apex.config

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
/**
 * Created by ksheppard on 23/12/2016.
 */
abstract class ApexConfiguration extends AbstractModule {

    private Vertx vertx;
    private HttpServer server;
    private HttpServerOptions serverOptions;

    {
        vertx = Vertx.vertx();
        serverOptions = new HttpServerOptions(port: 3000)
    }

    @Override
    protected void configure() {

    }

    @Provides @Singleton
    Vertx getVertx() {
        return vertx
    }

    void setVertx(Vertx vertx) {
        this.vertx = vertx
    }

    @Provides @Singleton
    HttpServer getServer() {
        server = vertx.createHttpServer(serverOptions)
        return server
    }

    HttpServerOptions serverConfig() {
        return serverOptions
    }
}
