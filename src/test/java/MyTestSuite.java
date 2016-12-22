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
public class MyTestSuite {

    public static void main(String[] args) {
//        ((Logger) LoggerFactory.getLogger("io.netty")).setLevel(Level.INFO);
//        org.slf4j.Logger logger = LoggerFactory.getLogger(MyTestSuite.class);
        TestSuite suite = TestSuite.create("apex-test-suite");
        Vertx vertx = Vertx.vertx();
        final HttpServer server = vertx.createHttpServer();

        suite.before(ctx -> {
            Async async = ctx.async();
            Router router = Router.router(vertx);
            router.route("/").handler(context -> { context.response().setStatusCode(200).end("Hello!"); });
            server.requestHandler(router::accept).listen(8000, result -> {
                ctx.assertTrue(result.succeeded());
                async.complete();
            });
        });

        suite.test("basic test", context -> {
            String s = "string";
            context.assertEquals("string", s);
        });

        suite.test("ping google", context -> {
            Async async = context.async();
            HttpClient client = vertx.createHttpClient();
            HttpClientRequest req = client.get("google.com", "/");
            req.handler(response -> {
                int code = response.statusCode();
                context.assertEquals(code, 302);
                async.complete();
            });
            req.end();
        });

        suite.test("local web request", ctx -> {
            Async async = ctx.async();
            HttpClient client = vertx.createHttpClient();
            HttpClientRequest req = client.get(8000, "localhost", "/");
            req.handler(response -> {
                int code = response.statusCode();
                ctx.assertEquals(code, 200);
                async.complete();
            });
            req.end();
        });

        suite.after(context -> {
            vertx.close(context.asyncAssertSuccess());
        });

        suite.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));
    }
}
