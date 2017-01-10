import com.reviselabs.apex.ApexApplication;

/**
 * Created by ksheppard on 21/12/2016.
 */
public class TestApplication {

    public static void main(String[] args) {
        ApexApplication app = new ApexApplication(new MyConfig());

        app.get("/",
                ctx -> ctx.put("name", "Kevin Sheppard").next(),
                ctx -> ctx.ok().end("Hello, " + ctx.get("name")))
        .get("/custom/:name", ctx -> {});

        app.mount("/sub", Sub.class).start();
    }
}
