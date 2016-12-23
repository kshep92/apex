import com.reviselabs.apex.ApexApplication

public class ExampleApplication {

    static ApexApplication create() {

        def app = new ApexApplication();

        app.parseRequestBody();

        app.get("/", { ctx -> ctx.ok().close('OK') });

        app.post("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });

        app.put("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });

        app.delete("/", { ctx -> ctx.ok().close('DELETED') });

        return app;
    }
}
