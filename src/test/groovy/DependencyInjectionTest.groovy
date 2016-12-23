import io.vertx.ext.unit.TestSuite

/**
 * Created by ksheppard on 23/12/2016.
 */
class DependencyInjectionTest extends AppTestSuite {

    static void main(args) {
        TestSuite.create("Dependency Injection Tests")
        .before({ startServer(it) })
        .test("Delegate is not null", { context ->
            context.assertTrue(app.delegate.router != null)
        })
        .test('Can receive requests', {context ->
            promise(context)
            client.get('/', { res ->
                context.assertEquals(200, res.statusCode())
                resolve()
            }).end()
        })
        .after({app.stop(it.asyncAssertSuccess())})
        .run(defaultTestOptions)
    }
}
