import io.vertx.ext.unit.TestSuite

class StaticAssetsTest extends AppTestSuite {

    static main(args) {
        TestSuite.create("Static Assets Tests")
        .before({context -> startServer(context)})
        .test("Check current directory", { context ->
            println(System.getProperty("user.dir"))
        })
        .test("Serve static JavaScript file", {context ->
            promise(context)
            client.get("/assets/app.js", {response ->
                context.assertEquals(200, response.statusCode())
                context.assertEquals(response.getHeader("content-type"), 'application/javascript')
                response.bodyHandler({ body -> context.assertTrue(body.toString().contains("function")); resolve(); })
            }).end()
        })
        .test("Serve static HTML file", {context ->
            promise(context)
            client.get("/assets/index.html", {response ->
                context.assertEquals(200, response.statusCode())
                context.assertEquals(response.getHeader("content-type"), 'text/html;charset=UTF-8')
                response.bodyHandler({ body -> context.assertTrue(body.toString().contains("<body>")); resolve(); })
            }).end()
        })
        .test("Serve static CSS file", {context ->
            promise(context)
            client.get("/assets/style.css", {response ->
                context.assertEquals(200, response.statusCode())
                context.assertEquals(response.getHeader("content-type"), 'text/css;charset=UTF-8')
                response.bodyHandler({ body -> context.assertTrue(body.toString().contains("body")); resolve(); })
            }).end()
        })
        .after({context -> app.stop(context.asyncAssertSuccess())})
        .run(defaultTestOptions)
    }
}
