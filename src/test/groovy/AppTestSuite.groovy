

import com.reviselabs.apex.ApexApplication
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpClientOptions
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestContext


class AppTestSuite {

    static ApexApplication app;
    static Async async;
    static HttpClient client;

    static void promise(TestContext context) {
        async = context.async();
    }

    static void resolve() {
        async.complete();
    }

    static void startServer(TestContext context) {
        promise(context)
        app = ExampleApplication.run()
        app.listen(3000, { resolve() });
        client = app.vertx.createHttpClient(new HttpClientOptions([defaultHost: 'localhost', defaultPort: 3000]));
    }
}
