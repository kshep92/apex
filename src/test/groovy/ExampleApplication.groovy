import com.reviselabs.apex.ApexApplication;

public class ExampleApplication {

    static ApexApplication run() {

        def testApp = new ApexApplication();

        testApp.parseRequestBody();
        testApp.get("/", { ctx -> ctx.ok().close('OK') });
        testApp.post("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });
        testApp.put("/", { ctx ->
            ctx.ok().close(ctx.body.toString())
        });
        testApp.delete("/", { ctx -> ctx.ok().close('DELETED') });

        return testApp;
    }
}
