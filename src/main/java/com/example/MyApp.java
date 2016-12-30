package com.example;

import com.reviselabs.apex.ApexApplication;
import com.reviselabs.apex.config.ApexConfiguration;
import com.reviselabs.apex.config.Environment;
import io.vertx.ext.web.templ.MVELTemplateEngine;

public class MyApp {
    public static void main(String[] args) {
        ApexApplication application = new ApexApplication(new ConfigModule());
        if(Environment.isDev()) application.getDefaultStaticHandler().setCachingEnabled(false);
        application
                .assets("/assets/*", "src/test/files")
                .parseCookies()
                .get("/", context -> context.renderText("Hello, world!"))
                .get("/template", context -> context.render("index"))
                .get("/cookies", context -> context.renderText(context.getCookie("mail").getValue()));

        application.start();
    }

    public static class ConfigModule extends ApexConfiguration {
        @Override
        protected void configure() {
            getServerOptions().setPort(3000);
            getTemplateEngine(MVELTemplateEngine.class).setExtension(".tppl");
            setTemplatesDirectory("public/views");
        }
    }
}
