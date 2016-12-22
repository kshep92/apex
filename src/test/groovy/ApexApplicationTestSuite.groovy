import com.reviselabs.apex.ApexApplication
import com.reviselabs.apex.RoutingComponent
import com.reviselabs.apex.web.RoutingContext;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestOptions;
import io.vertx.ext.unit.TestSuite;
import io.vertx.ext.unit.report.ReportOptions;

/**
 * Created by Kevin on 12/21/2016.
 */
public class ApexApplicationTestSuite {

    public static void main(String[] args) {
        TestSuite tests = TestSuite.create("ApexApplication Tests");
        final ApexApplication app = new ApexApplication();

        tests.before({context ->
            // Start the application and configure routes
            Async async = context.async();
            // TODO: Find out why this isn't working
            app.get("/", new RoutingComponent.RequestHandler() {
                            void handle(RoutingContext _context) {
                                _context.response()
                                        .setStatusCode(200)
                                        .end("OK!");
                            }
                    });
            app.listen(3000, {result -> async.complete() });
        });

        tests.after({context ->
            // Shut down the application
            app.stop(context.asyncAssertSuccess());
        });

        tests.run(new TestOptions().addReporter(new ReportOptions().setTo("console")));
    }
}
