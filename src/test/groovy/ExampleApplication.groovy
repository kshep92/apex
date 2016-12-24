import com.reviselabs.apex.ApexApplication
import config.TestConfig
import routes.SubRoutes
import test.data.Database

public class ExampleApplication {

    static ApexApplication create() {

        def app = new ApexApplication(new TestConfig());
        app.parseRequestBody();

        app.get("/", { ctx -> ctx.ok().close('OK') });

        app.get('/database_info', { ctx ->
            def url = ctx.getInstance(Database).url
            ctx.ok().close(url)
        })

        app.post("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });

        app.put("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });

        app.delete("/", { ctx -> ctx.ok().close('DELETED') });

        app.mount("/sub", SubRoutes)

        return app;
    }
}
