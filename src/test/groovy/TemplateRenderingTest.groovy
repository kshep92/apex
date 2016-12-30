import io.vertx.ext.unit.TestSuite

/**
 * Created by ksheppard on 30/12/2016.
 */
class TemplateRenderingTest extends AppTestSuite {

    static void main(args) {
        TestSuite.create("Template Rendering Tests")
        .before({context -> startServer(context)})
        .test("Render HTML file", {context ->
            client.get("/greeting", { res ->
                context.assertEquals(200, res.statusCode())
                res.bodyHandler({body ->
                    context.assertTrue(body.toString().contains("Hello, world"))
                    resolve()
                })
            }).end()
        })
        .after({context -> app.stop(context.asyncAssertSuccess())})
        .run(defaultTestOptions)
    }
}
