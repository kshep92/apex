package com.reviselabs.apex.config

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions

//TODO: Template engine configuration (MVEL)
abstract class ApexConfiguration extends AbstractModule {

    Vertx vertx;
    HttpServer server;
    HttpServerOptions serverOptions;

    ApexConfiguration() {
        vertx = Vertx.vertx();
        serverOptions = new HttpServerOptions(port: 3000)
    }

    @Provides @Singleton
    Vertx getVertx() {
        return vertx
    }

    @Provides @Singleton
    HttpServer getServer() {
        server = vertx.createHttpServer(serverOptions)
        return server
    }
}
