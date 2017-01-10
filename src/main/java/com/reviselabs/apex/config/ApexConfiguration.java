package com.reviselabs.apex.config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.templ.TemplateEngine;

public abstract class ApexConfiguration extends AbstractModule {

    private Vertx vertx;
    private HttpServerOptions serverOptions;
    private TemplateEngine templateEngine;
    private String templatesDirectory;

    {
        vertx = Vertx.vertx();
        serverOptions = new HttpServerOptions().setPort(3000);
    }

    public HttpServerOptions getServerOptions() {
        return serverOptions;
    }

    @Provides @Singleton
    public Vertx getVertx() {
        return vertx;
    }

    @Provides @Singleton
    public HttpServer getServer() {
        return vertx.createHttpServer(serverOptions);
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Provides
    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public <T> T getTemplateEngine(Class<T> engineClass) {
        return engineClass.cast(templateEngine);
    }

    @Provides @Named("templates")
    public String getTemplatesDirectory() {
        return templatesDirectory == null ? "templates" : templatesDirectory;
    }

    public void setTemplatesDirectory(String templatesDirectory) {
        this.templatesDirectory = templatesDirectory;
    }
}
