import io.vertx.ext.unit.TestSuite
import test.data.DAO
import test.data.Database

class DependencyInjectionTest extends AppTestSuite {

    static void main(args) {
        TestSuite.create("Dependency Injection Tests")
        .before({ startServer(it) })
        .test("Delegate is initialized", { context ->
            context.assertTrue(app.delegate.router != null)
        })
        .test("Can receive requests", {context ->
            promise(context)
            client.get('/', { res ->
                context.assertEquals(200, res.statusCode())
                resolve()
            }).end()
        })
        .test("Database class is injected properly", { context ->
            def dao = app.getInstance(DAO)
            context.assertNotNull(app.getInstance(Database).user)
            context.assertEquals(app.getInstance(Database).user, 'admin')
            context.assertNotNull(dao.database)
            context.assertEquals(dao.database.user, 'admin')
        })
        .test("RoutingContext can create instances", { context ->
            promise(context)
            client.get('/database_info', { res ->
                res.bodyHandler({body ->
                    context.assertEquals(body.toString(), 'mysql.com')
                    resolve()
                })
            }).end()
        })
        .after({app.stop(it.asyncAssertSuccess())})
        .run(defaultTestOptions)
    }
}
