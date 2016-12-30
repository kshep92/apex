import com.reviselabs.apex.ApexApplication
import config.TestConfig
import org.slf4j.LoggerFactory
import routes.SubRoutes
import test.data.Database

public class ExampleApplication {

    static ApexApplication create() {
        def logger = LoggerFactory.getLogger("ExampleApplication");

        def app = new ApexApplication(new TestConfig());

        app.parseRequestBody();

        app.assets("/assets/*", "src/test/files");

        app.get("/", { ctx -> ctx.ok().end('OK') });

        app.get('/database_info', { ctx ->
            def url = ctx.getInstance(Database).url
            ctx.ok().end(url)
        })

        app.get('/greeting', { ctx ->
            ctx.render("index", [title: "Greeting Page", greeting: "Hello, world!"]);
        })

        app.post("/", { ctx ->
            logger.debug("In a callback")
            ctx.ok().end(ctx.body.toString())
        });

        app.put("/", { ctx ->
            ctx.ok().end(ctx.body.toString())
        });

        app.delete("/", { ctx -> ctx.ok().end('DELETED') });

        app.mount("/sub", SubRoutes)

        return app;
    }
}
