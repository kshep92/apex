package com.reviselabs.apex.routing
import com.google.inject.Inject
import com.google.inject.name.Named
import com.reviselabs.apex.config.Environment
import com.reviselabs.apex.di.ApplicationContextContainer
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.templ.TemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.Nullable

class ApexRoutingContext implements ApplicationContextContainer {

    // In IntelliJ this will cause problems with Java files, but only in IntelliJ.
    @Delegate RoutingContext context;
    TemplateEngine templateEngine;
    String templatesFolder;
    Logger logger;

    @Inject
    ApexRoutingContext(@Named("templates") String templatesFolder, @Nullable TemplateEngine templateEngine) {
        this.templatesFolder = templatesFolder
        this.templateEngine = templateEngine
        logger = LoggerFactory.getLogger(getClass())
    }

    ApexRoutingContext ok() {
        response().setStatusCode(HttpURLConnection.HTTP_OK);
        return this;
    }

    ApexRoutingContext badRequest() {
        response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);
        return this;
    }

    ApexRoutingContext forbidden() {
        response().setStatusCode(HttpURLConnection.HTTP_FORBIDDEN);
        return this;
    }

    ApexRoutingContext error() {
        response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        return this;
    }

    void end(String message = '') {
        response().end(message);
    }

    void redirect(String location) {
        response().setStatusCode(HttpURLConnection.HTTP_MOVED_TEMP).putHeader("location", location)
    }

    void renderJson(String body, int statusCode = 200) {
        response()
                .setStatusCode(statusCode)
                .putHeader("content-type", "application/json")
                .putHeader("content-length", String.valueOf(body.length()))
                .write(body).end();
    }

    void renderText(String text, int statusCode = 200) {
        response()
                .setStatusCode(statusCode)
                .putHeader("Content-Type", "text/plain")
                .putHeader("content-length", String.valueOf(text.length()))
                .write(text).end()
    }

    void render(String template, Map data = [:]) {
        data.forEach({ String k, Object v -> this.put(k, v) })
        if(templateEngine) {
            templateEngine.render(this, "$templatesFolder/$template", { result ->
                def response = response().putHeader("Content-Type", "text/html");
                if(result.succeeded()) {
                    response.setStatusCode(200)
                            .putHeader("content-length", String.valueOf(result.result().length()))
                            .write(result.result())
                            .end();
                } else {
                    response.setStatusCode(500)
                    StringBuilder message = new StringBuilder();
                    message.append('<div style="font-family: sans-serif"> <h1>Rendering Error Occurred</h1>')
                    message.append("<p>${result.cause().message}</p>")
                    if(Environment.isDev()) {
                        message.append('<ul style="list-style: none">')
                        result.cause().stackTrace.each { trace ->
                            message.append("<li>${trace}</li>")
                        }
                        message.append("</ul>")
                    }
                    logger.error(result.cause().message)
                    message.append("</div>")
                    response.putHeader("content-length", String.valueOf(message.toString().length()))
                            .write(message.toString()).end()
                }
            })
        } else {
            renderText("No template engine defined.", 500)
        };
    }
}
