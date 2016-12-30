package com.reviselabs.apex.config
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.templ.MVELTemplateEngine
import io.vertx.ext.web.templ.TemplateEngine

abstract class ApexConfiguration extends AbstractModule {

    Vertx vertx;
    HttpServer server;
    HttpServerOptions serverOptions;
    TemplateEngine templateEngine;
    String templatesDirectory;

    ApexConfiguration() {
        vertx = Vertx.vertx();
        serverOptions = new HttpServerOptions(port: 3000)
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
        return templateEngine
    }

    public <T> T getTemplateEngine(Class<T> engineClass) {
        return engineClass.cast(templateEngine)
    }

    @Provides @Named("templates")
    String getTemplatesDirectory() {
        return templatesDirectory?: "templates"
    }


}
