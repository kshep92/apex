import com.reviselabs.apex.ApexApplication;
import com.reviselabs.apex.web.routing.SubRouter;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;
import io.vertx.ext.web.Router;

/**
 * Created by ksheppard on 21/12/2016.
 */
public class TestApplication {

    public static void main(String[] args) {
        ApexApplication app = new ApexApplication(new MyConfig());

        app.get("/",
                ctx -> ctx.put("name", "Kevin Sheppard").next(),
                ctx -> ctx.ok().close("Hello, " + ctx.get("name")))
        .get("/custom/:name", ctx -> {});

        app.mount("/sub", Sub.class).start();
    }
}
