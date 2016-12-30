package com.reviselabs.apex.config

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.templ.MVELTemplateEngine
import io.vertx.ext.web.templ.TemplateEngine

//TODO: Template engine configuration (MVEL)
abstract class ApexConfiguration extends AbstractModule {

    Vertx vertx;
    HttpServer server;
    HttpServerOptions serverOptions;
    TemplateEngine templateEngine;

    ApexConfiguration() {
        vertx = Vertx.vertx();
        serverOptions = new HttpServerOptions(port: 3000)
        //TODO: Figure out how to set the template directory
        templateEngine = MVELTemplateEngine.create()
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

    @Provides
    TemplateEngine getTemplateEngine() {
        return templateEngine;
    }


}
