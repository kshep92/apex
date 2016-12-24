

import com.reviselabs.apex.ApexApplication
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.TestOptions
import io.vertx.ext.unit.report.ReportOptions


class AppTestSuite {

    static ApexApplication app;
    static Async async;
    static HttpClient client;
    static TestOptions defaultTestOptions = new TestOptions().addReporter(new ReportOptions().setTo("console"))

    static void promise(TestContext context) {
        async = context.async();
    }

    static void resolve() {
        async.complete();
    }

    static void startServer(TestContext context) {
        promise(context)
        app = ExampleApplication.create()
        app.start(3000, { resolve() });
        client = app.vertx.createHttpClient(new HttpClientOptions(defaultHost: 'localhost', defaultPort: 3000));
    }
}
